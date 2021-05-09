package cepsearcher.services;

import cepsearcher.converters.AddressDTOToAddress;
import cepsearcher.converters.AddressToAddressDTO;
import cepsearcher.converters.ViaCEPAddressDTOToAddress;
import cepsearcher.domains.Address;
import cepsearcher.dtos.AddressDTO;
import cepsearcher.dtos.ViaCEPAddressDTO;
import cepsearcher.repositories.imperative.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class AddressServiceImplTest {
    @Mock
    RestTemplate restTemplate;

    @Mock
    AddressRepository addressRepository;

    String viaCepHost = "HOST_MOCK";
    String viaCepFormat = "FORMAT_MOCK";
    AddressToAddressDTO addressToAddressDTO;
    AddressDTOToAddress addressDTOToAddress;
    ViaCEPAddressDTOToAddress viaCEPAddressDTOToAddress;
    AddressService addressService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        addressRepository.deleteAll();
        this.addressToAddressDTO = new AddressToAddressDTO();
        addressDTOToAddress = new AddressDTOToAddress();
        viaCEPAddressDTOToAddress = new ViaCEPAddressDTOToAddress();
        this.addressService = new AddressServiceImpl(viaCepHost,
                viaCepFormat,
                restTemplate,
                addressRepository,
                addressToAddressDTO,
                addressDTOToAddress,
                viaCEPAddressDTOToAddress);
    }

    @Test
    public void findByCEPWithCallToWebservice() throws Exception {
        ViaCEPAddressDTO viaCEPAddressDTO = new ViaCEPAddressDTO();
        String mockCEP = "11700460";
        viaCEPAddressDTO.setCep(mockCEP);
        viaCEPAddressDTO.setBairro("MOCK");
        viaCEPAddressDTO.setComplemento("MOCK");
        viaCEPAddressDTO.setDdd("MOCK");
        viaCEPAddressDTO.setGia("MOCK");
        viaCEPAddressDTO.setSiafi("MOCK");
        viaCEPAddressDTO.setUf("MOCK");
        viaCEPAddressDTO.setLogradouro("MOCK");
        viaCEPAddressDTO.setIbge("MOCK");
        viaCEPAddressDTO.setLocalidade("MOCK");

        when(restTemplate.getForObject(this.viaCepHost + mockCEP + this.viaCepFormat, ViaCEPAddressDTO.class)).thenReturn(viaCEPAddressDTO);

        when(addressRepository.findByCep(mockCEP)).thenReturn(null);

        AddressDTO actualAddressDTO = this.addressService.findByCEP(mockCEP);

        Address expectedAddress = this.viaCEPAddressDTOToAddress.convert(viaCEPAddressDTO);
        AddressDTO expectedAddressDTO = this.addressToAddressDTO.convert(expectedAddress);

        assertEquals(actualAddressDTO.getCep(), expectedAddressDTO.getCep());
        assertEquals(actualAddressDTO.getStreet(), expectedAddressDTO.getStreet());
        assertEquals(actualAddressDTO.getCity(), expectedAddressDTO.getCity());
        verify(addressRepository, times(1)).findByCep(mockCEP);
        verify(addressRepository, times(1)).save(expectedAddress);
    }

}

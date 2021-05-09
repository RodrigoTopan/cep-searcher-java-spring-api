package cepsearcher.controllers;

import cepsearcher.dtos.AddressDTO;
import cepsearcher.services.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AddressControllerImplTest {
    @Mock
    AddressService addressService;

    AddressController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        controller = new AddressController(addressService);
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/api/address/11700460"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByCep() throws Exception {


        AddressDTO expectedAddressResponse = new AddressDTO();
        expectedAddressResponse.setDistrict("DISTRICT_MOCK");
        expectedAddressResponse.setState("STATE_MOCK");
        expectedAddressResponse.setCity("CITY_MOCK");
        expectedAddressResponse.setCep("1000");

        String cep = "1000";
        when(addressService.findByCEP(cep)).thenReturn(expectedAddressResponse);

        //when
        AddressDTO actualAddressResponse = controller.find(cep);


        //then
        assertEquals(actualAddressResponse, expectedAddressResponse);
        verify(addressService, times(1)).findByCEP(cep);
    }
}

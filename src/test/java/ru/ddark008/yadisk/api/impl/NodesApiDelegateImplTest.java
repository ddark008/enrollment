package ru.ddark008.yadisk.api.impl;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NodesApiDelegateImplTest {

    private final String VALIDATION_FALED = """
            {
              "code": 400,
              "message": "Validation Failed"
            }
            """;

    private final String NOT_FOUND = """
            {
              "code": 404,
              "message": "Item not found"
            }
            """;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void oneLevel() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                  "items": [
                                    {
                                      "id": "элемент_1_1",
                                      "type": "FILE",
                                      "url": "aliqua et temp",
                                      "parentId": null,
                                      "size": 93490855
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01.000Z"
                                }
                                """)
        ).andExpect(status().isOk());

        MvcResult result  = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/nodes/элемент_1_1")
        ).andExpect(status().isOk()).andReturn();
        JSONObject json = new JSONObject(result.getResponse().getContentAsString());
        System.out.println(json.toString(4));
    }

    @Test
    public void mixLevel() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                  "items": [
                                    {
                                      "id": "элемент_1",
                                      "type": "FOLDER",
                                      "url": null,
                                      "parentId": null,
                                      "size": null
                                    },
                                    {
                                      "id": "элемент_1_1",
                                      "type": "FILE",
                                      "url": "aliqua et temp",
                                      "parentId": "элемент_1",
                                      "size": 1
                                    },
                                    {
                                      "id": "элемент_1_2",
                                      "type": "FILE",
                                      "url": "aliqua et temp",
                                      "parentId": "элемент_1",
                                      "size": 2
                                    },
                                    {
                                      "id": "элемент_1_3",
                                      "type": "FOLDER",
                                      "url": null,
                                      "parentId": "элемент_1",
                                      "size": null
                                    },
                                    {
                                      "id": "элемент_1_3_1",
                                      "type": "FILE",
                                      "url": "aliqua et temp",
                                      "parentId": "элемент_1_3",
                                      "size": 3
                                    },
                                    {
                                      "id": "элемент_1_3_2",
                                      "type": "FILE",
                                      "url": "aliqua et temp",
                                      "parentId": "элемент_1_3",
                                      "size": 4
                                    },
                                    {
                                      "id": "элемент_1_4",
                                      "type": "FOLDER",
                                      "url": null,
                                      "parentId": "элемент_1",
                                      "size": null
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01Z"
                                }
                                                                """)
        ).andExpect(status().isOk());

        MvcResult result  = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/nodes/элемент_1")
        ).andExpect(status().isOk()).andReturn();
        JSONObject json = new JSONObject(result.getResponse().getContentAsString());
        System.out.println(json.toString(4));
    }

    @Test
    public void notFound() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/nodes/элемент_1")
        ).andExpect(status().isNotFound()).andExpect(content().json(NOT_FOUND));
    }


}
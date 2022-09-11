package ru.ddark008.yadisk.api.impl;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
// Тестовая БД, не требует postgresql, запускается на время тестов
@AutoConfigureEmbeddedDatabase(refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD, provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY)
class DeleteApiDelegateImplTest {

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
    public void delete() throws Exception {
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
                                      "parentId": "элемент_1_2",
                                      "size": 93490855
                                    },
                                    {
                                      "id": "элемент_1_2",
                                      "type": "FOLDER",
                                      "url": null,
                                      "parentId": null,
                                      "size": null
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01.000Z"
                                }
                                """)
        ).andExpect(status().isOk());

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/delete/элемент_1_2?date=2022-05-28T21:12:01.516Z")
        ).andExpect(status().isOk());
        System.out.println();
    }

    @Test
    public void badDate() throws Exception {
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
                                      "parentId": "элемент_1_2",
                                      "size": 93490855
                                    },
                                    {
                                      "id": "элемент_1_2",
                                      "type": "FOLDER",
                                      "url": null,
                                      "parentId": null,
                                      "size": null
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01.000Z"
                                }
                                """)
        ).andExpect(status().isOk());

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/delete/элемент_1_2?date=21")
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FALED));
    }

    @Test
    public void noDate() throws Exception {
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
                                      "parentId": "элемент_1_2",
                                      "size": 93490855
                                    },
                                    {
                                      "id": "элемент_1_2",
                                      "type": "FOLDER",
                                      "url": null,
                                      "parentId": null,
                                      "size": null
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01.000Z"
                                }
                                """)
        ).andExpect(status().isOk());

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/delete/элемент_1_2")
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FALED));
    }

    @Test
    public void notFound() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/delete/test?date=2022-05-28T21:12:01.516Z")
        ).andExpect(status().isNotFound()).andExpect(content().json(NOT_FOUND));
    }
}
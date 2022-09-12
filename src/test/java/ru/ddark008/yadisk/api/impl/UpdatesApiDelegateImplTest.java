package ru.ddark008.yadisk.api.impl;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
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
import static ru.ddark008.yadisk.api.impl.ErrorValues.VALIDATION_FAILED;

@SpringBootTest
@AutoConfigureMockMvc
// Тестовая БД, не требует postgresql, запускается на время тестов
@AutoConfigureEmbeddedDatabase(refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD, provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY)
class UpdatesApiDelegateImplTest {
    @Autowired
    private MockMvc mockMvc;

    public void importData() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "items": [
                                    {
                                      "id": "2",
                                      "type": "FILE",
                                      "url": "test",
                                      "parentId": "1",
                                      "size": 1
                                    },
                                    {
                                      "id": "1",
                                      "type": "FOLDER",
                                      "url": null,
                                      "parentId": null,
                                      "size": null
                                    }
                                  ],
                                  "updateDate": "2022-05-28T00:00:00.000Z"
                                }
                                """)
        ).andExpect(status().isOk());
        // Дата создания ровно 24 часа спустя
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "items": [
                                    {
                                      "id": "3",
                                      "type": "FILE",
                                      "url": "test",
                                      "parentId": "1",
                                      "size": 2
                                    }
                                  ],
                                  "updateDate": "2022-05-29T00:00:00.000Z"
                                }
                                """)
        ).andExpect(status().isOk());
        // Дата создания 24 часа спустя + 1 мс
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "items": [
                                    {
                                      "id": "4",
                                      "type": "FILE",
                                      "url": "test1",
                                      "parentId": "1",
                                      "size": 2
                                    }
                                  ],
                                  "updateDate": "2022-05-29T00:00:00.001Z"
                                }
                                """)
        ).andExpect(status().isOk());
        // Дата создания внутри периода
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "items": [
                                    {
                                      "id": "5",
                                      "type": "FILE",
                                      "url": "test1",
                                      "parentId": "1",
                                      "size": 2
                                    }
                                  ],
                                  "updateDate": "2022-05-28T01:00:00.001Z"
                                }
                                """)
        ).andExpect(status().isOk());
    }

    /**
     * Проверка, что измененные файлы входят в период [date - 24h, date]
     * @throws Exception
     */
    @Test
    public void testPeriod() throws Exception {
        // Загрузка данных
        importData();
        String result_json = """
                {
                  "items": [
                    {
                      "date": "2022-05-28T00:00:00Z",
                      "size": 1,
                      "id": "2",
                      "type": "FILE",
                      "url": "test",
                      "parentId": "1"
                    },
                    {
                      "date": "2022-05-29T00:00:00Z",
                      "size": 2,
                      "id": "3",
                      "type": "FILE",
                      "url": "test",
                      "parentId": "1"
                    },
                    {
                      "date": "2022-05-28T01:00:00.001Z",
                      "size": 2,
                      "id": "5",
                      "type": "FILE",
                      "url": "test1",
                      "parentId": "1"
                    }
                  ]
                }
                """;
        MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/updates?date=2022-05-29T00:00:00.000Z")
        ).andExpect(status().isOk()).andExpect(content().json(result_json)).andReturn();
    }

    @Test
    public void badDate() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/updates?date=1")
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FAILED));
    }

    @Test
    public void noDate() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/updates")
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FAILED));
    }

}
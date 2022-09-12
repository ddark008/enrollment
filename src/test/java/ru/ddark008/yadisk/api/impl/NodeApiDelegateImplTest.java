package ru.ddark008.yadisk.api.impl;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
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
import static ru.ddark008.yadisk.api.impl.ErrorValues.NOT_FOUND;
import static ru.ddark008.yadisk.api.impl.ErrorValues.VALIDATION_FAILED;

@SpringBootTest
@AutoConfigureMockMvc
// Тестовая БД, не требует postgresql, запускается на время тестов
@AutoConfigureEmbeddedDatabase(refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD, provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY)
class NodeApiDelegateImplTest {
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
        // Изменение папки и файла
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
                                      "size": 2
                                    }
                                  ],
                                  "updateDate": "2022-05-28T00:00:01.000Z"
                                }
                                """)
        ).andExpect(status().isOk());
        // Изменение URL файла (размер папки не меняется)
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "items": [
                                    {
                                      "id": "2",
                                      "type": "FILE",
                                      "url": "test1",
                                      "parentId": "1",
                                      "size": 2
                                    }
                                  ],
                                  "updateDate": "2022-05-29T00:00:00.000Z"
                                }
                                """)
        ).andExpect(status().isOk());
        // Изменение папки
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "items": [
                                    {
                                      "id": "1",
                                      "type": "FOLDER",
                                      "url": null,
                                      "parentId": null,
                                      "size": null
                                    }
                                  ],
                                  "updateDate": "2022-05-29T00:00:00.001Z"
                                }
                                """)
        ).andExpect(status().isOk());
    }

    /**
     * Проверка, что история получается за заданный полуинтервал [from, to).
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
                      "date": "2022-05-28T00:00:01Z",
                      "size": 2,
                      "id": "2",
                      "type": "FILE",
                      "url": "test",
                      "parentId": "1"
                    }
                  ]
                }
                """;
        MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/node/2/history?dateStart=2022-05-28T00:00:00.000Z&dateEnd=2022-05-29T00:00:00.000Z")
        ).andExpect(status().isOk()).andExpect(content().json(result_json)).andReturn();
//        JSONObject json = new JSONObject(result.getResponse().getContentAsString());
//        System.out.println(json.toString(4));
    }

    /**
     * Проверка, что формат папок правильный
     * @throws Exception
     */
    @Test
    public void testFolder() throws Exception {
        // Загрузка данных
        importData();
        String result_json = """
            {
              "items": [
                {
                  "date": "2022-05-28T00:00:00Z",
                  "size": 1,
                  "id": "1",
                  "type": "FOLDER",
                  "url": null,
                  "parentId": null
                },
                {
                  "date": "2022-05-28T00:00:01Z",
                  "size": 2,
                  "id": "1",
                  "type": "FOLDER",
                  "url": null,
                  "parentId": null
                }
              ]
            }
                """;
        MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/node/1/history?dateStart=2022-05-28T00:00:00.000Z&dateEnd=2022-05-29T00:00:00.000Z")
        ).andExpect(status().isOk()).andExpect(content().json(result_json)).andReturn();
//        JSONObject json = new JSONObject(result.getResponse().getContentAsString());
//        System.out.println(json.toString(4));
    }

    /**
     * Проверка, что история удаляется после удаления элемента
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception {
        // Загрузка данных
        importData();
        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/delete/2?date=2022-05-28T21:12:01.516Z")
        ).andExpect(status().isOk());

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/node/2/history?dateStart=2022-05-28T00:00:00.000Z&dateEnd=2022-05-29T00:00:00.000Z")
        ).andExpect(status().isNotFound()).andExpect(content().json(NOT_FOUND));
    }

    /**
     * Проверка, что даты не перепутаны местами
     * @throws Exception
     */
    @Test
    public void testDateChange() throws Exception {
        // Загрузка данных
        importData();
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/node/2/history?dateStart=2022-05-30T00:00:00.000Z&dateEnd=2022-05-29T00:00:00.000Z")
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FAILED));
    }

    /**
     * Проверка, дата начала и конца равны друг другу
     * @throws Exception
     */
    @Test
    public void testDateOne() throws Exception {
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
                    }
                  ]
                }
                """;
        MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/node/2/history?dateStart=2022-05-28T00:00:00.000Z&dateEnd=2022-05-28T00:00:00.000Z")
        ).andExpect(status().isOk()).andExpect(content().json(result_json)).andReturn();
    }

    @Test
    public void notFound() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/node/222/history?dateStart=2022-05-28T00:00:00.000Z&dateEnd=2022-05-29T00:00:00.000Z")
        ).andExpect(status().isNotFound()).andExpect(content().json(NOT_FOUND));
    }

    @Test
    public void badDate1() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/node/2/history?dateStart=1&dateEnd=2022-05-29T00:00:00.000Z")
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FAILED));
    }

    @Test
    public void badDate2() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/node/2/history?dateStart=2022-05-29T00:00:00.000Z&dateEnd=2")
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FAILED));
    }

    @Test
    public void noDate() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/node/2/history")
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FAILED));
    }


}
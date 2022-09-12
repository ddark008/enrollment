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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ddark008.yadisk.api.impl.ErrorValues.VALIDATION_FAILED;

@SpringBootTest
@AutoConfigureMockMvc
// Тестовая БД, не требует postgresql, запускается на время тестов
@AutoConfigureEmbeddedDatabase(refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD, provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY)
class ImportsApiDelegateImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void twoUnits() throws Exception {
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
    }

    @Test
    public void sameIDs() throws Exception {
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
                                      "id": "элемент_1_1",
                                      "type": "FOLDER",
                                      "url": null,
                                      "parentId": null,
                                      "size": null
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01.000Z"
                                }
                                                                """)
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FAILED));
    }

    @Test
    public void nullId() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "items": [
                                    {
                                      "id": null,
                                      "type": "FILE",
                                      "url": "aliqua et temp",
                                      "parentId": "элемент_1_2",
                                      "size": 93490855
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01.000Z"
                                }
                                                                """)
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FAILED));
    }

    @Test
    public void parentFileNew() throws Exception {
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
                                      "type": "FILE",
                                      "url": "test",
                                      "parentId": null,
                                      "size": 2
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01.000Z"
                                }
                                                                """)
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FAILED));
    }

    @Test
    public void parentFileFromDB() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                  "items": [
                                    {
                                      "id": "элемент_1_2",
                                      "type": "FILE",
                                      "url": "test",
                                      "parentId": null,
                                      "size": 2
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01Z"
                                }
                                                                """)
        ).andExpect(status().isOk());
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
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01Z"
                                }
                                                                """)
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FAILED));
    }

    @Test
    public void folderUrlNonNull() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "items": [
                                    {
                                      "id": "элемент_1_1",
                                      "type": "FOLDER",
                                      "url": "aliqua et temp",
                                      "parentId": null,
                                      "size": null
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01.000Z"
                                }
                                                                """)
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FAILED));
    }

    @Test
    public void folderSizeNonNull() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "items": [
                                    {
                                      "id": "элемент_1_1",
                                      "type": "FOLDER",
                                      "url": null,
                                      "parentId": null,
                                      "size": 122
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01.000Z"
                                }
                                                                """)
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FAILED));
    }

    @Test
    public void fileUrlNull() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "items": [
                                    {
                                      "id": "элемент_1_1",
                                      "type": "FILE",
                                      "url": null,
                                      "parentId": null,
                                      "size": 1
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01.000Z"
                                }
                                                                """)
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FAILED));
    }

    @Test
    public void fileUrlMore255() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "items": [
                                    {
                                      "id": "элемент_1_1",
                                      "type": "FILE",
                                      "url": "mzyprwxhpxisppjmobpjelrsrudoxmrdcyfedcggwfrmrlkebgnhwzziexzobgmtvbhaypaqyblsnatpkiirtittzmrquuegiemwluqvbjncimceawboxwfctpckjcxoarruibgjenkiotovotoxwpwjtgbrhtmntivugrtrwmornqycumlypkjscuiishuzfvzlslpvrxsctsksjdcaxoiaoqpzztprrjhrbhnzrugibmpwragvdwftaxmrbgxh",
                                      "parentId": null,
                                      "size": 1
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01.000Z"
                                }
                                                                """)
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FAILED));
    }

    @Test
    public void fileSizeNull() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "items": [
                                    {
                                      "id": "элемент_1_1",
                                      "type": "FILE",
                                      "url": "1",
                                      "parentId": null,
                                      "size": null
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01.000Z"
                                }
                                                                """)
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FAILED));
    }

    @Test
    public void fileSizeNegative() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "items": [
                                    {
                                      "id": "элемент_1_1",
                                      "type": "FILE",
                                      "url": "1",
                                      "parentId": null,
                                      "size": -1
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01.000Z"
                                }
                                                                """)
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FAILED));
    }

    @Test
    public void replaceParent() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                  "items": [
                                    {
                                      "id": "1_1",
                                      "type": "FILE",
                                      "url": "aliqua et temp",
                                      "parentId": "1_2",
                                      "size": 93490855
                                    },
                                    {
                                      "id": "1_2",
                                      "type": "FOLDER",
                                      "url": null,
                                      "parentId": null,
                                      "size": null
                                    },
                                    {
                                      "id": "1_3",
                                      "type": "FOLDER",
                                      "url": null,
                                      "parentId": null,
                                      "size": null
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01Z"
                                }
                                                                """)
        ).andExpect(status().isOk());
        String result_json = """
                {
                  "date": "2022-05-28T21:12:01Z",
                  "size": 93490855,
                  "children": [
                    {
                      "date": "2022-05-28T21:12:01Z",
                      "size": 93490855,
                      "children": null,
                      "id": "1_1",
                      "type": "FILE",
                      "url": "aliqua et temp",
                      "parentId": "1_2"
                    }
                  ],
                  "id": "1_2",
                  "type": "FOLDER",
                  "url": null,
                  "parentId": null
                }
                """;
        MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/nodes/1_2")
        ).andExpect(status().isOk()).andExpect(content().json(result_json)).andReturn();
//        JSONObject json = new JSONObject(result.getResponse().getContentAsString());
//        System.out.println(json.toString(4));

        result_json = """
                {
                    "date": "2022-05-28T21:12:01Z",
                    "size": 0,
                    "children": [],
                    "id": "1_3",
                    "type": "FOLDER",
                    "url": null,
                    "parentId": null
                }
                """;
        result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/nodes/1_3")
        ).andExpect(status().isOk()).andExpect(content().json(result_json)).andReturn();

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                  "items": [
                                    {
                                      "id": "1_1",
                                      "type": "FILE",
                                      "url": "aliqua et temp",
                                      "parentId": "1_3",
                                      "size": 5
                                    }
                                  ],
                                  "updateDate": "2023-05-28T21:13:01Z"
                                }
                                                                """)
        ).andExpect(status().isOk());

        result_json = """
                {
                    "date": "2023-05-28T21:13:01Z",
                    "size": 0,
                    "children": [],
                    "id": "1_2",
                    "type": "FOLDER",
                    "url": null,
                    "parentId": null
                }
                """;
        result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/nodes/1_2")
        ).andExpect(status().isOk()).andExpect(content().json(result_json)).andReturn();

        result_json = """
                {
                    "date": "2023-05-28T21:13:01Z",
                    "size": 5,
                    "children": [
                        {
                            "date": "2023-05-28T21:13:01Z",
                            "size": 5,
                            "children": null,
                            "id": "1_1",
                            "type": "FILE",
                            "url": "aliqua et temp",
                            "parentId": "1_3"
                        }
                    ],
                    "id": "1_3",
                    "type": "FOLDER",
                    "url": null,
                    "parentId": null
                }
                """;
        result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/nodes/1_3")
        ).andExpect(status().isOk()).andExpect(content().json(result_json)).andReturn();
    }

    @Test
    public void sizeParent() throws Exception {
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
                                    },
                                    {
                                      "id": "1_1",
                                      "type": "FILE",
                                      "url": "aliqua et temp",
                                      "parentId": "1",
                                      "size": 1
                                    },
                                    {
                                      "id": "1_2",
                                      "type": "FILE",
                                      "url": "aliqua et temp",
                                      "parentId": "1",
                                      "size": 2
                                    },
                                    {
                                      "id": "1_3",
                                      "type": "FOLDER",
                                      "url": null,
                                      "parentId": "1",
                                      "size": null
                                    },
                                    {
                                      "id": "1_3_1",
                                      "type": "FILE",
                                      "url": "aliqua et temp",
                                      "parentId": "1_3",
                                      "size": 3
                                    },
                                    {
                                      "id": "1_3_2",
                                      "type": "FILE",
                                      "url": "aliqua et temp",
                                      "parentId": "1_3",
                                      "size": 4
                                    },
                                    {
                                      "id": "1_4",
                                      "type": "FOLDER",
                                      "url": null,
                                      "parentId": "1",
                                      "size": null
                                    }
                                  ],
                                  "updateDate": "2022-05-28T21:12:01Z"
                                }
                                                                """)
        ).andExpect(status().isOk());

        String result_json = """
                {
                  "id": "1",
                  "type": "FOLDER",
                  "url": null,
                  "parentId": null,
                  "size": 10,
                  "date": "2022-05-28T21:12:01Z",
                  "children": [
                    {
                      "id": "1_1",
                      "type": "FILE",
                      "url": "aliqua et temp",
                      "parentId": "1",
                      "size": 1,
                      "children": null,
                      "date": "2022-05-28T21:12:01Z"
                    },
                    {
                      "id": "1_2",
                      "type": "FILE",
                      "url": "aliqua et temp",
                      "parentId": "1",
                      "size": 2,
                      "children": null,
                      "date": "2022-05-28T21:12:01Z"
                    },
                    {
                      "id": "1_3",
                      "type": "FOLDER",
                      "url": null,
                      "parentId": "1",
                      "size": 7,
                      "date": "2022-05-28T21:12:01Z",
                      "children": [
                        {
                          "id": "1_3_1",
                          "type": "FILE",
                          "url": "aliqua et temp",
                          "parentId": "1_3",
                          "size": 3,
                          "children": null,
                          "date": "2022-05-28T21:12:01Z"
                        },
                        {
                          "id": "1_3_2",
                          "type": "FILE",
                          "url": "aliqua et temp",
                          "parentId": "1_3",
                          "size": 4,
                          "children": null,
                          "date": "2022-05-28T21:12:01Z"
                        }
                      ]
                    },
                    {
                      "id": "1_4",
                      "type": "FOLDER",
                      "url": null,
                      "parentId": "1",
                      "size": 0,
                      "children": [],
                      "date": "2022-05-28T21:12:01Z"
                    }
                  ]
                }
                """;
        MvcResult result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/nodes/1")
        ).andExpect(status().isOk()).andExpect(content().json(result_json)).andReturn();
//        JSONObject json = new JSONObject(result.getResponse().getContentAsString());
//        System.out.println(json.toString(4));

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/imports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                  "items": [
                                    {
                                      "id": "1_3_2",
                                      "type": "FILE",
                                      "url": "aliqua et temp",
                                      "parentId": "1_4",
                                      "size": 4
                                    }
                                  ],
                                  "updateDate": "2023-05-28T21:13:02Z"
                                }
                                                                """)
        ).andExpect(status().isOk());

        result_json = """
                {
                  "id": "1",
                  "type": "FOLDER",
                  "url": null,
                  "parentId": null,
                  "size": 10,
                  "date": "2023-05-28T21:13:02Z",
                  "children": [
                    {
                      "id": "1_1",
                      "type": "FILE",
                      "url": "aliqua et temp",
                      "parentId": "1",
                      "size": 1,
                      "children": null,
                      "date": "2022-05-28T21:12:01Z"
                    },
                    {
                      "id": "1_2",
                      "type": "FILE",
                      "url": "aliqua et temp",
                      "parentId": "1",
                      "size": 2,
                      "children": null,
                      "date": "2022-05-28T21:12:01Z"
                    },
                    {
                      "id": "1_3",
                      "type": "FOLDER",
                      "url": null,
                      "parentId": "1",
                      "size": 3,
                      "date": "2023-05-28T21:13:02Z",
                      "children": [
                        {
                          "id": "1_3_1",
                          "type": "FILE",
                          "url": "aliqua et temp",
                          "parentId": "1_3",
                          "size": 3,
                          "children": null,
                          "date": "2022-05-28T21:12:01Z"
                        }
                      ]
                    },
                    {
                      "id": "1_4",
                      "type": "FOLDER",
                      "url": null,
                      "parentId": "1",
                      "size": 4,
                      "children": [
                        {
                          "id": "1_3_2",
                          "type": "FILE",
                          "url": "aliqua et temp",
                          "parentId": "1_4",
                          "size": 4,
                          "children": null,
                          "date": "2023-05-28T21:13:02Z"
                        }
                      ],
                      "date": "2023-05-28T21:13:02Z"
                    }
                  ]
                }
                """;
        result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/nodes/1")
        ).andExpect(status().isOk()).andExpect(content().json(result_json)).andReturn();
    }
}
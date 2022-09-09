package ru.ddark008.yadisk.api.impl;

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
class ImportsApiDelegateImplTest {

    private final String VALIDATION_FALED = """
            {
              "code": 400,
              "message": "Validation Failed"
            }
            """;

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
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FALED));
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
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FALED));
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
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FALED));
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
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FALED));
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
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FALED));
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
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FALED));
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
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FALED));
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
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FALED));
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
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FALED));
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
        ).andExpect(status().isBadRequest()).andExpect(content().json(VALIDATION_FALED));
    }

}
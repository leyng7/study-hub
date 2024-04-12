package com.studyhub.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "43.201.149.42", uriPort = 8080)
@ExtendWith(RestDocumentationExtension.class)
public class RestDocSetupTest {

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation).operationPreprocessors()
                        .withRequestDefaults(
                                prettyPrint(),
                                modifyHeaders()
                                        .remove("Content-Length")
                                        .remove("Host"))
                        .withResponseDefaults(
                                prettyPrint(),
                                modifyHeaders()
                                        .remove("Vary")
                                        .remove("X-Content-Type-Options")
                                        .remove("X-XSS-Protection")
                                        .remove("Cache-Control")
                                        .remove("Pragma")
                                        .remove("Expires")
                                        .remove("X-Frame-Options")
                                        .remove("Content-Length")))
//                    .alwaysDo(document("{class-name}/{method-name}"))
                .build();
    }

}

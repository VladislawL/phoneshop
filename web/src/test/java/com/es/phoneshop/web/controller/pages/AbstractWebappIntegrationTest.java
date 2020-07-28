package com.es.phoneshop.web.controller.pages;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = {"classpath:applicationContext.xml",
        "classpath:dispatcher-servlet.xml"})
@WebAppConfiguration
abstract public class AbstractWebappIntegrationTest {
}

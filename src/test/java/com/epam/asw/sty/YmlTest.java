package com.epam.asw.sty;

import com.epam.asw.sty.dao.YmlImportRepository;
import com.epam.asw.sty.service.YmlImportService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.annotation.Resource;


@RunWith(MockitoJUnitRunner.class)
public class YmlTest extends AbstractTestNGSpringContextTests {


    @InjectMocks
    YmlImportService ymlImportService;

    @Mock
    YmlImportRepository ymlImportRepository;

	@Test
    @Ignore
	public void testDogeService() throws Exception {
        Assert.assertTrue(ymlImportService.requiresYml());
	}

    @Test
    @Ignore
    public void testDogeRepository() throws Exception {
        Assert.assertEquals("wow", ymlImportRepository.getYmlImportData(), "Test Repository was not injected");
    }
}
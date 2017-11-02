package com.emcloud.nfs.cucumber.stepdefs;

import com.emcloud.nfs.EmCloudNfsApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = EmCloudNfsApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}

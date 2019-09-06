package com.jfilter.mock.webservice;

import com.jfilter.filter.FieldFilterSetting;
import com.jfilter.filter.SessionStrategies;
import com.jfilter.filter.SessionStrategy;
import com.jfilter.mock.MockClassesHelper;
import com.jfilter.mock.MockUser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

@SessionStrategies({
        @SessionStrategy(attributeName = "ROLE", attributeValue = "ADMIN", ignoreFields = {
                @FieldFilterSetting(fields = {"id", "password"})
        }),
        @SessionStrategy(attributeName = "ROLE", attributeValue = "ADMIN", ignoreFields = {
                @FieldFilterSetting(fields = {"email"})
        })
})

public class WSClassStrategies {
    public static final String MAPPING_SIGN_IN_STRATEGIES = "/strategies/customers/signIn";

    @RequestMapping(value = MAPPING_SIGN_IN_STRATEGIES,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClassesHelper.getUserMock();
    }

}
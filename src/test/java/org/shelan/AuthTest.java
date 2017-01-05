package org.shelan;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.shelan.exception.AuthentcatorException;
import org.shelan.model.Model;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

/**
 * Test Authentication
 */
public class AuthTest {


    @Test
    public void testAuth() throws AuthentcatorException {

        Model model = EasyMock.createMock(Model.class);
        expect(model.getPasswordHash("authtest")).andReturn("$2a$10$GVgsxXejDEAqV9I2." +
                "zDoGOHhyitGcs8Xa/8kayL0RPtjv74o46g/C");
        expect(model.addAccessLog("authtest",true)).andReturn(true);

        replay(model);
        Controller controller = new Controller(model);
        Status token = controller.authenticate("authtest", "password");
        Assert.assertEquals(Status.SUCCESS, token);

    }

    @Test
    public void testAuthFail() throws AuthentcatorException {

        Model model = EasyMock.createMock(Model.class);
        expect(model.getPasswordHash("authtest")).andReturn("$2a$10$GVgsxXejDEAqV9I2." +
                "zDoGOHhyitGcs8Xa/8kayL0RPtjv74o46g/C");
        expect(model.addAccessLog("authtest",false)).andReturn(false);

        replay(model);
        Controller controller = new Controller(model);
        Status token = controller.authenticate("authtest", "password2");
        Assert.assertEquals(Status.FAILED, token);

    }
}

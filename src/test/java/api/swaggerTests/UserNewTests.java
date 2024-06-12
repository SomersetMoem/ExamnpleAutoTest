package api.swaggerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testng.Assert;
import test.org.listeners.AdminUser;
import test.org.listeners.AdminUserResolver;
import test.org.models.swagger.FullUser;
import test.org.services.UserService;

import java.util.List;

import static test.org.assertions.conditions.Conditions.hasMessage;
import static test.org.assertions.conditions.Conditions.hasStatusCode;
import static test.org.utils.RandomTestData.*;


@ExtendWith(AdminUserResolver.class)
public class UserNewTests {
    private static UserService userService;
    private FullUser user;

    @BeforeEach
    public void initTestUser() {
        user = getRandomFullUser();
    }


    @Test
    public void positiveRegisterTest() {
        userService.register(user)
                .should(hasStatusCode(201))
                .should(hasMessage("User created"));
    }

    @Test
    public void positiveRegisterWithGamesTest() {
        FullUser user = getRandomUserWithGames();

        userService.register(user)
                .should(hasStatusCode(201))
                .should(hasMessage("User created"));
    }

    @Test
    public void negativeRegisterLoginExistsTest() {
        userService.register(user);
        userService.register(user)
                .should(hasStatusCode(400))
                .should(hasMessage("Login already exist"));
    }

    @Test
    public void negativeRegisterNoPasswordTest() {
        user.setPass(null);
        userService.register(user)
                .should(hasStatusCode(400))
                .should(hasMessage("Missing login or password"));
    }

    @Test
    public void positiveAdminAuthTest(@AdminUser FullUser admin) {
        String token = userService.auth(admin)
                .should(hasStatusCode(200))
                .asJwt();

        Assert.assertNotNull(token);
    }

    @Test
    public void positiveNewUserAuthTest() {
        userService.register(user);
        String token = userService.auth(user)
                .should(hasStatusCode(200))
                .asJwt();

        Assert.assertNotNull(token);
    }

    @Test
    public void negativeAuthTest() {
        userService.auth(user).should(hasStatusCode(401));
    }

    @Test
    public void positiveGetUserInfoTest() {
        FullUser user = getAdminUser();
        String token = userService.auth(user).asJwt();
        userService.getUserInfo(token)
                .should(hasStatusCode(201));
    }

    @Test
    public void positiveGetUserInfoInvalidJwtTest() {
        userService.getUserInfo("fake jwt")
                .should(hasStatusCode(400));
    }

    @Test
    public void negativeGetUserInfoWithoutJwtTest() {
        userService.getUserInfo(null)
                .should(hasStatusCode(401));
    }

    @Test
    public void positiveChangeUserPassTest() {
        userService.register(user);
        String token = userService.auth(user).asJwt();

        String updatePassword = "newPas";

        userService.uploadPassword(updatePassword, token)
                .should(hasStatusCode(200))
                .should(hasMessage("User password successfully changed"));

        user.setPass(updatePassword);
        token = userService.auth(user).asJwt();

        FullUser updateUser = userService.getUserInfo(token).as(FullUser.class);

        Assert.assertNotEquals(user.getPass(), updateUser.getPass());
    }

    @Test
    public void positiveChangeAdminPassTest() {
        FullUser user = getAdminUser();

        String token = userService.auth(user).asJwt();

        String updatePassword = "newPassUpdate";
        userService.uploadPassword(updatePassword, token)
                .should(hasStatusCode(400))
                .should(hasMessage("Cant update base users"));
    }

    @Test
    public void negativeDeleteAdminTest() {
        FullUser user = getAdminUser();

        String token = userService.auth(user).asJwt();
        userService.deleteUser(token)
                .should(hasStatusCode(400))
                .should(hasMessage("Cant delete base users"));
    }

    @Test
    public void positiveDeleteNewUserTest() {
        userService.register(user);
        String token = userService.auth(user).asJwt();

        userService.deleteUser(token)
                .should(hasStatusCode(200))
                .should(hasMessage("User successfully deleted"));
    }

    @Test
    public void positiveGetAllUsersTest() {
        List<String> users = userService.getAllUsers().asList(String.class);

        Assert.assertTrue(users.size() >= 3);
    }
}

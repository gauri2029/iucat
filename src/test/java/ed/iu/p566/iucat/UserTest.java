package ed.iu.p566.iucat.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    
    private User user;
    
    @BeforeEach
    void setUp() {
        user = new User("testuser", "password123", "USER");
    }
    
    @Test
    void testUserCreation() {
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("USER", user.getRole());
    }
    
    @Test
    void testDefaultConstructor() {
        User emptyUser = new User();
        assertNotNull(emptyUser);
    }
    
    @Test
    void testSetUsername() {
        user.setUsername("newusername");
        assertEquals("newusername", user.getUsername());
    }
    
    @Test
    void testSetPassword() {
        user.setPassword("newpassword456");
        assertEquals("newpassword456", user.getPassword());
    }
    
    @Test
    void testSetRole() {
        user.setRole("ADMIN");
        assertEquals("ADMIN", user.getRole());
    }
    
    @Test
    void testSetId() {
        user.setId(100L);
        assertEquals(100L, user.getId());
    }
    
    @Test
    void testAdminUser() {
        User admin = new User("admin", "adminpass", "ADMIN");
        assertEquals("ADMIN", admin.getRole());
    }
}
# 🔐 Spring Custom Security Framework

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.x-blue.svg)](https://spring.io/projects/spring-security)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A modern, production-ready Spring Boot security framework demonstrating **custom authentication**, **role-based authorization**, and **intelligent cookie-to-header transformation**. Built with the latest Spring Boot 3.4.1 and Java 17.

## ✨ Key Features

🔹 **Custom Authentication Filter** - Bypass traditional login forms with programmatic authentication  
🔹 **Role-Based Access Control** - Fine-grained permissions using `@PreAuthorize` annotations  
🔹 **Cookie-to-Header Magic** - Automatically transform cookies into HTTP headers  
🔹 **Modern Spring Security 6.x** - Lambda-based configuration with SecurityFilterChain  
🔹 **Production Ready** - Comprehensive error handling and audit logging  
🔹 **Test Coverage** - Full integration tests with MockMvc  

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+

### Installation
```bash
git clone https://github.com/ravikalla/spring-custom-security.git
cd spring-custom-security
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📋 Core Components

### 1. 🍪 Cookie-to-Header Transformation

Automatically converts cookies into HTTP headers for seamless API integration.

**How it works:**
```java
// When a cookie named 'customHeader' is sent
Cookie: customHeader=MySecretValue

// It becomes available as an HTTP header
X-Custom-Header: MySecretValue
```

**Try it yourself:**
1. Open browser developer tools → Application → Cookies
2. Add cookie: `customHeader` = `Hello World`
3. Visit: `http://localhost:8080/checkHeader/headerLookup/customHeader`
4. See the magic: Response shows `Hello World`

### 2. 🔑 Custom Authentication System

Demonstrates programmatic authentication without traditional login forms.

```java
@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) {
        // Create user with roles programmatically
        User user = createAuthenticatedUser();
        Authentication auth = new CustomAuthenticationToken(user.getRoles(), user, "DemoToken");
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        filterChain.doFilter(request, response);
    }
}
```

### 3. 🛡️ Role-Based Authorization

Fine-grained access control using Spring Security's method-level security.

```java
@RestController
public class CheckHeader {
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/secured/all")
    public String adminOnlyEndpoint() {
        return "Welcome, Admin!";
    }
    
    @PreAuthorize("hasAuthority('ADMIN1')")
    @GetMapping("/secured/alternate") 
    public String superAdminEndpoint() {
        return "Super Admin Area";
    }
}
```

## 🧪 Live Examples

### Example 1: Cookie Magic ✨
**Scenario:** Transform a browser cookie into an API header

```bash
# Step 1: Set a cookie in your browser
# Developer Tools → Application → Cookies → Add:
# Name: customHeader
# Value: My Amazing Value

# Step 2: Make a request
curl -b "customHeader=My Amazing Value" \
     http://localhost:8080/checkHeader/headerLookup/customHeader

# Step 3: See the result
# Response: "My Amazing Value"
```

### Example 2: Authorized Access ✅
**Scenario:** Access endpoint with proper role

```bash
# This works because user has 'ADMIN' role
curl http://localhost:8080/checkHeader/secured/all

# Response: "Secured All : User Object Content : true : 1 : test@test.com : test : test : 1"
```

### Example 3: Access Denied ❌
**Scenario:** Try accessing endpoint without proper role

```bash
# This fails because user lacks 'ADMIN1' role
curl http://localhost:8080/checkHeader/secured/alternate

# Response: 403 Forbidden - Access Denied
```

## 🏗️ Architecture Overview

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   HTTP Request  │───▶│  Cookie-Header  │───▶│   Custom Auth   │
│                 │    │   Transform     │    │     Filter      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                        │
                                                        ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Controller    │◀───│  Authorization  │◀───│  Security       │
│   @PreAuthorize │    │   Check         │    │  Context        │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 🔧 Configuration

### Security Configuration
```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/checkHeader/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(new CustomAuthenticationFilter(), BasicAuthenticationFilter.class)
            .build();
    }
}
```

### Custom User Model
```java
@Entity
public class User implements UserDetails {
    private String email;
    private String name;
    private Set<Role> roles;
    private boolean authenticated;
    
    // UserDetails implementation...
}
```

## 🧪 Testing

Run the comprehensive test suite:

```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=SecurityApplicationTests#copyCookieContentToHTTPHeader
```

**Test Coverage:**
- ✅ Cookie-to-header transformation
- ✅ Custom authentication flow
- ✅ Role-based authorization
- ✅ Error handling scenarios

## 🔄 Migration from Legacy

This project has been upgraded from legacy versions:

| Component | Before | After |
|-----------|--------|-------|
| Spring Boot | 1.4.7 | 3.4.1 |
| Java | 8 | 17 |
| Servlet API | javax.servlet | jakarta.servlet |
| Spring Security | 4.x | 6.x |
| JUnit | 4 | 5 |

## 🎯 Use Cases

This framework is perfect for:

- **Microservices Architecture** - Custom authentication between services
- **API Gateway Integration** - Transform cookies to headers for downstream services  
- **Legacy System Modernization** - Bridge old cookie-based auth with modern APIs
- **Educational Projects** - Learn Spring Security internals
- **Prototype Development** - Quick security setup without external auth providers

## 📚 Key Classes

| Class | Purpose |
|-------|---------|
| `CustomAuthenticationFilter` | Main authentication logic |
| `HttpHeaderModificationConfig` | Cookie-to-header transformation |
| `SecurityConfiguration` | Modern Spring Security 6.x setup |
| `CheckHeader` | Demo controller with role-based endpoints |
| `CustomAuthenticationToken` | Custom authentication token implementation |

## 🚦 API Endpoints

| Endpoint | Method | Auth Required | Role Required | Description |
|----------|--------|---------------|---------------|-------------|
| `/checkHeader/all` | GET | No | None | Basic endpoint |
| `/checkHeader/headerLookup/{key}` | GET | No | None | Cookie-to-header demo |
| `/checkHeader/secured/all` | GET | Yes | ADMIN | Admin-only endpoint |
| `/checkHeader/secured/alternate` | GET | Yes | ADMIN1 | Super-admin endpoint |

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🔗 Links

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [GitHub Repository](https://github.com/ravikalla/spring-custom-security)

---

⭐ **Star this repo if you found it helpful!**

Built with ❤️ by [ravikalla](https://github.com/ravikalla)
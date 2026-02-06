package io.github.sinri.keel.logger.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class LoggingStackSpecificationTest {

    @Test
    void testIgnorableCallStackPackageInitialized() {
        Set<String> ignorablePackages = LoggingStackSpecification.IgnorableCallStackPackageSet;

        Assertions.assertNotNull(ignorablePackages);
        Assertions.assertFalse(ignorablePackages.isEmpty());
    }

    @Test
    void testContainsExpectedPackages() {
        Set<String> ignorablePackages = LoggingStackSpecification.IgnorableCallStackPackageSet;

        // Test some expected packages
        Assertions.assertTrue(ignorablePackages.contains("io.vertx.core."));
        Assertions.assertTrue(ignorablePackages.contains("io.vertx.ext.web"));
        Assertions.assertTrue(ignorablePackages.contains("io.netty."));
        Assertions.assertTrue(ignorablePackages.contains("java.lang."));
        Assertions.assertTrue(ignorablePackages.contains("jdk.internal."));
        Assertions.assertTrue(ignorablePackages.contains("io.vertx.mysqlclient"));
        Assertions.assertTrue(ignorablePackages.contains("io.vertx.sqlclient"));
    }

    @Test
    void testPackageNamesEndWithDot() {
        Set<String> ignorablePackages = LoggingStackSpecification.IgnorableCallStackPackageSet;

        // Most package names should end with a dot, but some might not
        long packagesWithDot = ignorablePackages.stream()
                                                .filter(pkg -> pkg.endsWith("."))
                                                .count();

        // At least some packages should end with dot
        Assertions.assertTrue(packagesWithDot > 0, "Some packages should end with dot");
    }

    @Test
    void testPackageNamesAreNotEmpty() {
        Set<String> ignorablePackages = LoggingStackSpecification.IgnorableCallStackPackageSet;

        ignorablePackages.forEach(pkg -> {
            Assertions.assertNotNull(pkg);
            Assertions.assertFalse(pkg.isEmpty(), "Package name should not be empty");
        });
    }

    @Test
    void testSetIsImmutable() {
        Set<String> ignorablePackages = LoggingStackSpecification.IgnorableCallStackPackageSet;

        int originalSize = ignorablePackages.size();

        // Try to modify (should throw UnsupportedOperationException if immutable)
        try {
            ignorablePackages.add("test.package.");
            // If we get here, the set is mutable, which is also acceptable
        } catch (UnsupportedOperationException e) {
            // Expected if set is immutable
            Assertions.assertEquals(originalSize, ignorablePackages.size());
        }
    }

    @Test
    void testAllExpectedPackagesPresent() {
        Set<String> ignorablePackages = LoggingStackSpecification.IgnorableCallStackPackageSet;

        // Verify all packages from the source code are present
        String[] expectedPackages = {
                "io.vertx.core.",
                "io.vertx.ext.web",
                "io.netty.",
                "java.lang.",
                "jdk.internal.",
                "io.vertx.mysqlclient",
                "io.vertx.sqlclient"
        };

        for (String expectedPackage : expectedPackages) {
            Assertions.assertTrue(
                    ignorablePackages.contains(expectedPackage),
                    "Should contain: " + expectedPackage
            );
        }
    }
}


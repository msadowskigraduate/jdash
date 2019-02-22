package io.zoran.infrastructure.services;

import io.zoran.domain.resource.ResourcePrivileges;
import io.zoran.domain.resource.shared.SharingGroup;

import java.util.function.Predicate;

import static io.zoran.domain.resource.ResourcePrivileges.READ;
import static io.zoran.domain.resource.ResourcePrivileges.REVOKED;
import static io.zoran.domain.resource.ResourcePrivileges.WRITE;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 27/01/2019.
 */
public class SharingGroupUtils {
    //TODO should be setup from properties
    public static ResourcePrivileges getDefaultPrivilege() {
        return READ;
    }

    public static Predicate<SharingGroup> filterRevoked(String ownerUserId) {
        return x -> !x.getPriviligesMap().get(ownerUserId).equals(REVOKED);
    }

    public static Predicate<SharingGroup> canReadOrWrite(String ownerUserId) {
        return x -> x.getAccessFor(ownerUserId).equals(READ) || x.getAccessFor(ownerUserId).equals(WRITE);
    }

    public static Predicate<SharingGroup> canRead(String ownerUserId) {
        return x -> x.getAccessFor(ownerUserId).equals(READ);
    }

    public static Predicate<SharingGroup> canWrite(String ownerUserId) {
        return x -> x.getAccessFor(ownerUserId).equals(WRITE);
    }
}

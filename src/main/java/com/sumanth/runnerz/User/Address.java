package com.sumanth.runnerz.User;

public record Address(
        String street,
        String city,
        String zipcode,
        Geo geo
) {
}

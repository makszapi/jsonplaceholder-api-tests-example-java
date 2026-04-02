package dev.maksymzapisov.jsonplaceholder.data;

import dev.maksymzapisov.jsonplaceholder.model.*;

public final class TestDataProvider {

    private TestDataProvider() {
    }

    public static User getExistingUser() {
        Geo geo = new Geo(
                "24.6463",
                "-168.8889"
        );

        Address address = new Address(
                "Dayna Park",
                "Suite 449",
                "Bartholomebury",
                "76495-3109",
                geo
        );

        Company company = new Company(
                "Yost and Sons",
                "Switchable contextually-based project",
                "aggregate real-time technologies"
        );

        return new User(
                9,
                "Glenna Reichert",
                "Delphine",
                "Chaim_McDermott@dana.io",
                address,
                "(775)976-6794 x41206",
                "conrad.com",
                company
        );
    }

    public static Post getPostFromExistingUser() {
        int userId = getExistingUser().getId();
        return new Post(
                userId,
                81,
                "tempora rem veritatis voluptas quo dolores vero",
                "facere qui nesciunt est voluptatum voluptatem nisi\nsequi eligendi necessitatibus ea at rerum itaque\nharum non ratione velit laboriosam quis consequuntur\nex officiis minima doloremque voluptas ut aut"
        );
    }
}

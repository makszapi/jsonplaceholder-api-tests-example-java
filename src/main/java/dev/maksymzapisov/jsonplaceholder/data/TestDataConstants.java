package dev.maksymzapisov.jsonplaceholder.data;

public final class TestDataConstants {

    private TestDataConstants() {
    }

    public static final String EXISTING_USER_JSON = """
            {
              "id": 9,
              "name": "Glenna Reichert",
              "username": "Delphine",
              "email": "Chaim_McDermott@dana.io",
              "address": {
                "street": "Dayna Park",
                "suite": "Suite 449",
                "city": "Bartholomebury",
                "zipcode": "76495-3109",
                "geo": {
                  "lat": "24.6463",
                  "lng": "-168.8889"
                }
              },
              "phone": "(775)976-6794 x41206",
              "website": "conrad.com",
              "company": {
                "name": "Yost and Sons",
                "catchPhrase": "Switchable contextually-based project",
                "bs": "aggregate real-time technologies"
              }
            }
            """;

    public static final String POST_FROM_EXISTING_USER_JSON = """
            {
              "userId": 9,
              "id": 81,
              "title": "tempora rem veritatis voluptas quo dolores vero",
              "body": "facere qui nesciunt est voluptatum voluptatem nisi\\nsequi eligendi necessitatibus ea at rerum itaque\\nharum non ratione velit laboriosam quis consequuntur\\nex officiis minima doloremque voluptas ut aut"
            }
            """;

    public static final String COMMENT_FOR_POST_FROM_EXISTING_USER_JSON = """
            {
              "postId": 81,
              "id": 403,
              "name": "architecto voluptatum eos blanditiis aliquam debitis beatae nesciunt dolorum",
              "email": "Patience_Bahringer@dameon.biz",
              "body": "et a et perspiciatis\\nautem expedita maiores dignissimos labore minus molestiae enim\\net ipsam ea et\\nperspiciatis veritatis debitis maxime"
            }
            """;
}
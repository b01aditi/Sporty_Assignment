# \#TwitchWebTesting

# Twitch UI Automation Framework is a Java-based UI automation project built with Playwright and JUnit 5, following the Page Object Model (POM) to ensure clean separation of concerns, scalability, and test readability. The framework supports execution in mobile-emulated and desktop Chrome modes via configuration, handles real-world UI challenges such as pop-ups and infinite scrolling, maintains test isolation through fresh browser contexts, and captures clean artifacts for debugging, making it suitable for CI/CD pipelines and technical interview demonstrations.

# \#APITesting

# \### Test Cases Summary Table

# | ID | Test Case Name | API Endpoint | Method | Input Data (Parametrized) | Expected Result |

# | :--- | :--- | :--- | :--- | :--- | :--- |

# | \*\*TC01\*\* | \*\*Retrieve Post\*\* | `/posts/{id}` | GET | `id=1` (Existing)<br>`id=99999` (Missing) | \*\*200 OK\*\* (Returns JSON with ID 1)<br>\*\*404 Not Found\*\* (Empty) |

# | \*\*TC02\*\* | \*\*Create New Post\*\* | `/posts` | POST | Payload: `{"title": "...", "body": "..."}` | \*\*201 Created\*\*<br>Response must contain new `id: 101` and match input data. |

# | \*\*TC03\*\* | \*\*Filter by User\*\* | `/posts` | GET | Query Param: `userId=1` | \*\*200 OK\*\*<br>List containing exactly 10 items; all items must have `userId: 1`. |

# | \*\*TC04\*\* | \*\*Delete Post\*\* | `/posts/1` | DELETE | N/A | \*\*200 OK\*\*<br>Response body should be empty `{}`. |

# \# Validation Strategy: Description

# \## Status Code Verification:

# Why: This is the primary gatekeeper. I explicitly checked for 201 on creation (not just 200) because strict REST standards dictate 201 Created for successful POST requests. I checked 404 for invalid IDs to ensure the API handles errors gracefully rather than crashing or returning false positives.

# \## Payload Echo Verification (Data Integrity):

# Why: In test\_create\_post, I asserted that data\["title"] == payload\["title"]. This confirms that the server actually received and parsed the data sent, rather than just returning a generic success message while discarding the content.

# \## Collection Logic Verification:

# Why: In test\_filter\_posts\_by\_user, I didn't just check if data was returned; I iterated through the entire list (for post in data) to ensure every single item belonged to the requested User ID. This catches "leaky" filters where an API might accidentally return all posts instead of the filtered ones.

# 

# 


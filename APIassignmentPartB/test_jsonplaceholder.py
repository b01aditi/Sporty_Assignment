import pytest
import requests

BASE_URL = "https://jsonplaceholder.typicode.com"

class TestJSONPlaceholder:

   
    # Test Case 1: GET Resource (Parametrized for different IDs)

    @pytest.mark.parametrize("post_id, expected_status", [
        (1, 200),        # Valid Post ID
        (99999, 404)     # Non-existent Post ID
    ])
    def test_get_post(self, post_id, expected_status):
        response = requests.get(f"{BASE_URL}/posts/{post_id}")
        assert response.status_code == expected_status
        
        if expected_status == 200:
            data = response.json()
            # Validation: Structure and Data Accuracy
            assert data["id"] == post_id
            assert "title" in data
            assert "body" in data

 
    # Test Case 2: POST Create Resource (Parametrized Payload)
   
    @pytest.mark.parametrize("payload", [
        {"title": "Pytest Demo", "body": "Automation is fun", "userId": 1},
        {"title": "API Testing", "body": "Check status 201", "userId": 5}
    ])
    def test_create_post(self, payload):
        response = requests.post(f"{BASE_URL}/posts", json=payload)
        
        # Validation: 201 Created is the specific success code for POST
        assert response.status_code == 201
        
        data = response.json()
        # Validation: Verify input is echoed back
        assert data["title"] == payload["title"]
        assert data["body"] == payload["body"]
        assert data["userId"] == payload["userId"]
        # Validation: Verify a new ID was assigned (fake API always returns 101)
        assert "id" in data
        assert data["id"] == 101 

  
    # Test Case 3: GET Filtering (Query Parameters)
  
    @pytest.mark.parametrize("user_id, expected_count", [
        (1, 10),  # User 1 has exactly 10 posts in this dataset
        (2, 10)   # User 2 also has 10 posts
    ])
    def test_filter_posts_by_user(self, user_id, expected_count):
        params = {"userId": user_id}
        response = requests.get(f"{BASE_URL}/posts", params=params)
        
        assert response.status_code == 200
        data = response.json()
        
        # Validation: Ensure list length matches expected count
        assert len(data) == expected_count
        # Validation: Ensure every item in list actually belongs to user_id
        for post in data:
            assert post["userId"] == user_id


    # Test Case 4: DELETE Resource
  
    def test_delete_post(self):
        # Trying to delete Post #1
        response = requests.delete(f"{BASE_URL}/posts/1")
        
        # Validation: 200 OK or 204 No Content is standard. 
        # JSONPlaceholder returns 200 with empty body.
        assert response.status_code == 200
        assert response.json() == {}
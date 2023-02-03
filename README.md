# Book Search
This is a simple book search app that makes an HTTP request to the Google Books API. The app is designed to handle bad HTTP responses in case it is null or partially incomplete, and it also has separate UI states for when the the request is successful, loading, or an error. In its current form, the app only shows images of book covers in a lazy vertical grid which I parsed using Moshi. 

To prepare for later versions of this app (which I am working on right now), I parsed the response for other objects, such as the titles, descriptions, and authors of queried books. 


## Video Demonstration
![Video Demonstration](https://user-images.githubusercontent.com/113391095/216455331-789f8f37-3fd0-468b-9d49-3a2cc7da959a.gif)

## Normal App Functionality
![No Search](https://user-images.githubusercontent.com/113391095/216455345-0d13477a-a561-4285-ae8b-84e874a53ab5.png)
![Search](https://user-images.githubusercontent.com/113391095/216455355-aa913ce0-6a98-493a-8138-4fcf385ae02e.png)

## Error Handling
Null Picture Response  | Invalid Search | No Internet
------------- | ------------- | -------------
![Search Error Picture](https://user-images.githubusercontent.com/113391095/216455358-cf61e027-f4ac-4050-8a05-a67c317460df.png)  | ![Search Error](https://user-images.githubusercontent.com/113391095/216455372-470949a4-6c16-47fb-bc06-3caed553e646.png) | ![Search No Internet](https://user-images.githubusercontent.com/113391095/216455386-47af1423-974d-4e53-9ecc-29f37233d934.png)


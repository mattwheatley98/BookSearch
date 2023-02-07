# Book Search
This is a book search app that makes an HTTP request to the Google Books API. The response is first displayed with a list of book covers for the user's search, and if users click on a book, it takes them to a different screen with much more information. The app is designed to handle bad HTTP responses in case it is null or partially incomplete, and it also has separate UI states for when the the request is successful, loading, or an error. All parsing was done with Moshi and many clean architecture principles were followed; when I learn how to better use mappers to completely isolate the domain layer, I will be sure to update this project, as well as my other projects, to follow this important principle of clean architecture.

**Added in a new feature that lets users click a book for more information!**

## Video Demonstrations
Book Search Feature  | NEW! Book Information Feature
------------- | -------------
<img src="https://user-images.githubusercontent.com/113391095/216455331-789f8f37-3fd0-468b-9d49-3a2cc7da959a.gif" width = 350 height = 700>  |  <img src="https://user-images.githubusercontent.com/113391095/217124295-da07d50e-1354-4c2f-9b97-904c3ce6d995.gif" width = 350 height = 700>

## Normal App Functionality
Before Searching  | Book Searching | NEW! Book Information Screen 
------------- | ------------- | -------------
![No Search](https://user-images.githubusercontent.com/113391095/216455345-0d13477a-a561-4285-ae8b-84e874a53ab5.png)  | ![Search](https://user-images.githubusercontent.com/113391095/216455355-aa913ce0-6a98-493a-8138-4fcf385ae02e.png) | <img src="https://user-images.githubusercontent.com/113391095/217125004-b2cc185e-4171-4cdd-bd93-6bb911ce1cd2.png" width = 310 height = 620>

## Error Handling
Null Picture Response  | Invalid Search | No Internet
------------- | ------------- | -------------
![Search Error Picture](https://user-images.githubusercontent.com/113391095/216455358-cf61e027-f4ac-4050-8a05-a67c317460df.png)  | ![Search Error](https://user-images.githubusercontent.com/113391095/216455372-470949a4-6c16-47fb-bc06-3caed553e646.png) | ![Search No Internet](https://user-images.githubusercontent.com/113391095/216455386-47af1423-974d-4e53-9ecc-29f37233d934.png)

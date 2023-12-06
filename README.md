<h1>RidePal Web Application</h1>
<p>RidePal is a web application that allows users to generate playlists tailored to their musical preferences for specific travel durations. The application utilizes external services such as Microsoft Bing Maps and Deezer to calculate travel durations and fetch music data. Pixabay's API is used to attach an image to a newly created playlist in the system.</p>

<br>
  <h2>Table of Contents</h2>
    <ul>
        <li><a href="#main-requirements">Main Functionalities</a></li>
        <li><a href="#public-part">Public Part</a></li>
        <li><a href="#private-part">Private Part</a></li>
        <li><a href="#administration-part">Administration Part</a></li>
        <li><a href="#rest-api">REST API</a></li>
        <li><a href="#external-services">External Services</a></li>
        <li><a href="#technologies">Technologies and Principles</a></li>
    </ul>
<br>
  <h2 id="#main-requirements">Main Functionalities</h2>
  <h3>Playlist Generation</h3>
   <ul>
     <li>Users can enter starting and destination addresses, select musical genres and adjust the each genre's percentage in a playlist, and click "Begin Journey" to create a playlist</li>
     <li>RidePal calculates travel duration and generates playlists with tracks from specified genres to match the travel time</li>
     <li>Playlists are saved under the user's profile for future listening</li>
   </ul>
<br>
   <h3>Playlist Browsing</h3>
   <ul>
     <li>Users can browse playlists created by other users, filter by total duration and genre tags</li>
     <li>Playlists are sorted by average rank in descending order by default</li>
   </ul>
<br>
   <h3>Configuration Options</h3>
   <ul>
     <li>Users can configure playlist generation algorithm options, including genre preferences and track preview</li>
     <li>There is an option to select 'Use Top Tracks', which will generate a playlist using the highest ranked tracks from the database</li>
     <li>There is an option to select 'Allow tracks from same artists', which </li>
   </ul>
<br>
   <h3>Genre Synchronization</h3>
   <ul>
   <li>Admins have the choice to fetch all genres from the Deezer's database by a single click</li>
   <li>By default, there is a time period after which all genres are automatically fetched from Deezer</li>
  <li>The time can be configured by all admins</li>
   </ul>
  
   <br>
   <br>
   <h2 id="#public-part">Public Part</h2>
    <ul>
      <li>Visible without authentication</li>
      <li>Users have access to the registration page, where they can create a new account</li>
      <li>On the login page there is an option to use Google as a third party authentication, which saves newcomers time for registration</li>
      <li>Unauthenticated users also have access to the home page, where they can see statistics about the website, top 10 playlists by rank and all genres.</li>
      <li>Lastly, the playlists page is also open for unauthenitcated users, where playlists can be filtered by genre, minimum/maximum duration in minutes</li>
    </ul>
    
<br>
   <br>
   <h2 id="#private-part">Private Part</h2>
    <ul>
      <li>Generating a new playlist</li>
      <li>Checking others users' accounts</li>
      <li>Editing/Deleting owned playlists</li>
      <li>Editing of a playlist lets user change its title and image</li>
   </ul>
<br>
   <br>
   <h2 id="#administration-part">Administration Part</h2>
   <h3>System administrators can administer all major information objects in the system. On top of the regular user capabilities, the administrators have the following capabilities:</h3>
    <ul>
      <li>Administrators are able to edit/delete users and other administrators</li>
      <li>Administrators are able to edit/delete over the playlists</li>
      <li>Administrators are able to manually trigger Genre synchronization and configure its the time period</li>
  </ul>

<br>
<br>

   <h2 id="#rest-api">REST API</h2>
  <ul>
    <li>Link to the documentation in Swagger -> http://localhost:8080/api (access after you have successfully configured the project)</li>
  </ul>
   
<br>
<br>
   <h2 id="#external-services">External Services</h2>


   <h3>Deezer's API (https://developers.deezer.com/api)</h3>
    <ul>
      <li>Used to fetch tracks, genres, albums and artists</li>
    </ul>

<br>
  <h3>Microsoft Bing Maps's Distance Matrix API (https://www.microsoft.com/en-us/maps/bing-maps/distance-matrix)</h3>
  <ul>
  <li>Used to calculate distance and duration between two wayponints. Each waypoint is represented as a combination of longitude and latitude</li>
  </ul>

  <br>
  <h3>Microsoft Bing Maps's Locations API (https://learn.microsoft.com/en-us/bingmaps/rest-services/locations/)</h3>
    <ul>
      <li>Used to get the latitude and longitude of the starting location and final destination, which are input as location names in our playlist generator.</li>
  </ul>

<br>
  <h3>Pixabay's API (https://pixabay.com/bg/service/about/api/)</h3>
  <ul>
      <li>Used to fetch an image every time a new playlist is created</li>
  </ul>
  <br>


  <h2 id="#technologies">Technologies and Principles</h2>
  <ul>
    <li>Java, Spring Boot, Spring MVC, Spring Security, Lombok, Thymeleaf, JUnit, Mockito, HTML, CSS, JavaScript, Hibernate, MariaDB</li>
    <li>DRY, KISS, YAGNI, SOLID, OOP, REST API design</li>
    <li>Service layer tests have over 80% code coverage</li>
    <li>Multilayered architecture</li>
  </ul>

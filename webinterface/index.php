<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../assets/ico/favicon.png">
    <script type="text/javascript"
          src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false">
     </script>
    <title>GT Campus Events</title>

    <!-- Bootstrap core CSS -->
    <link href="./dist/css/bootstrap.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="../../assets/js/html5shiv.js"></script>
      <script src="../../assets/js/respond.min.js"></script>
    <![endif]-->

    <!-- Custom styles for this template -->
    <link href="carousel.css" rel="stylesheet">

  </head>
<!-- NAVBAR
================================================== -->
  <body>
    <div class="navbar-wrapper">
      <div class="container">
        <div class="navbar navbar-inverse navbar-static-top">
          <div class="container">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="#">GT Campus Events</a>
            </div>
            <div class="navbar-collapse collapse">
              <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">Locations<b class="caret"></b></a>
                  <ul class="dropdown-menu">
		  <?php
			$curl = curl_init();
			curl_setopt($curl, CURLOPT_URL,"http://wesley-crusher.firba1.com:8080/api/v1.0/location/getlocations");
			curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
			$json = curl_exec($curl);
			curl_close($curl); 
			$jsonobj = json_decode($json);
			$locations = $jsonobj->locations;
      $i = 1;
      $locationArray = array();
			foreach($locations as $location){
        /*$curl = curl_init();
        curl_setopt($curl, CURLOPT_URL,"http://wesley-crusher.firba1.com:8080/api/v1.0/location/nametocoordinates/".$location);
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
        $json = curl_exec($curl);
        curl_close($curl); 
        $jsonobj = json_decode($json);*/
        $locationArray[i] = $location;
        //$latitude = $jsonobj->latitude;
				print "<li><a href=\"./locations.php?location=$location\">".$locationArray[i]."</a></li>";
        $i++;
			}
		  ?>
                  </ul>
                </li>
                <li><a href="#events">Upcoming Events</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
<!--Google Map with Markers-->
    <script type="text/javascript">
      var map;

      function initialize() {
        <?php
          $curl = curl_init();
          curl_setopt($curl, CURLOPT_URL,"http://wesley-crusher.firba1.com:8080/api/v1.0/location/nametocoordinates/Library");
          curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
          $json = curl_exec($curl);
          curl_close($curl); 
          $jsonobj = json_decode($json);
          $latitude = $jsonobj->latitude;
          $longitude = $jsonobj->longitude;
          ?>
        var mapOptions = {
          scrollwheel: false,
          center: new google.maps.LatLng(33.777142, -84.397582),
          zoom: 15,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById("map-canvas"),
            mapOptions);

        var studentCenter = new google.maps.Marker({
          position: new google.maps.LatLng(<?php echo json_encode($latitude); ?>, <?php echo json_encode($longitude); ?>),
          map: map,
          title:"Student Center"
        });
        var crc = new google.maps.Marker({
          position: new google.maps.LatLng(33.775646, -84.404174),
          map: map,
          title:<?php echo json_encode($locationArray[1]); ?>
        });
        var bookstore = new google.maps.Marker({
          position: new google.maps.LatLng(33.77665, -84.388557),
          map: map,
          title:"Barnes & Noble Bookstore"
        });

        // Try HTML5 geolocation
        if(navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(function(position) {
            var pos = new google.maps.LatLng(position.coords.latitude,
                                             position.coords.longitude);

            var infowindow = new google.maps.InfoWindow({
              map: map,
              position: pos,
              content: 'You are here.'
            });

            map.setCenter(pos);
          }, function() {
            handleNoGeolocation(true);
          });
        } else {
          // Browser doesn't support Geolocation
          handleNoGeolocation(false);
        }
      }

      function handleNoGeolocation(errorFlag) {
        if (errorFlag) {
          var content = 'Error: The Geolocation service failed.';
        } else {
          var content = 'Error: Your browser doesn\'t support geolocation.';
        }

        var options = {
          map: map,
          position: new google.maps.LatLng(60, 105),
          content: content
        };

        var infowindow = new google.maps.InfoWindow(options);
        map.setCenter(options.position);
      }
        /*google.maps.event.addListener(studentCenter, 'click', function() {
          info
        });*/
      google.maps.event.addDomListener(window, 'load', initialize);
    </script>

    <div class="google-map-canvas" id="map-canvas"> </div>
<!--List of Upcoming Events by Time Frame-->

  <div class="container">
    <div class="row">
      <div class="col-lg-12">
        <a name="events">
          <h1>Upcoming Events</h1>
        </a>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-4">
        <h2>Today</h2>
        <ul class="nav nav-pills nav-stacked">
          <?php
          $curl = curl_init();
          curl_setopt($curl, CURLOPT_URL,"http://wesley-crusher.firba1.com:8080/api/v1.0/location/geteventsfornexthours/12");
          curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
          $json = curl_exec($curl);
          curl_close($curl); 
          $jsonobj = json_decode($json);
          $jsonobj->events = array_slice($jsonobj->events,0,3);

          foreach($jsonobj->events as $event) {
            print "<li><a href=\"./events.php?event=$event->event_name&startdate=$event->start_date&starttime=$event->start_time\"><div>";
            print $event->event_name;
            print "</div>"; 
            print $event->start_date.' '.$event->start_time;
            print "</a></li>";
          }
        ?>
        </ul>
      </div>
      <div class="col-lg-4">
        <h2>Next 3 Days</h2>
        <ul class="nav nav-pills nav-stacked">
          <?php
          $curl = curl_init();
          curl_setopt($curl, CURLOPT_URL,"http://wesley-crusher.firba1.com:8080/api/v1.0/location/geteventsfornexthours/72");
          curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
          $json = curl_exec($curl);
          curl_close($curl); 
          $jsonobj = json_decode($json);
          $jsonobj->events = array_slice($jsonobj->events,0,3);

          foreach($jsonobj->events as $event) {
            $eventobj = json_encode($event);
            print "<li><a href=\"./events.php?event=$event->event_name&startdate=$event->start_date&starttime=$event->start_time\"><div>";
            print $event->event_name;
            print "</div>"; 
            print $event->start_date.' '.$event->start_time;
            print "</a></li>";
          }
        ?>
        </ul>
      </div>
      <div class="col-lg-4">
        <h2>Next 7 Days</h2>
        <ul class="nav nav-pills nav-stacked">
          <?php
          $curl = curl_init();
          curl_setopt($curl, CURLOPT_URL,"http://wesley-crusher.firba1.com:8080/api/v1.0/location/geteventsfornexthours/168");
          curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
          $json = curl_exec($curl);
          curl_close($curl); 
          $jsonobj = json_decode($json);
          $jsonobj->events = array_slice($jsonobj->events,0,3);

          foreach($jsonobj->events as $event) {
            print "<li><a href=\"./events.php?event=$event->event_name&startdate=$event->start_date&starttime=$event->start_time\"><div>";
            print $event->event_name;
            print "</div>"; 
            print $event->start_date.' '.$event->start_time;
            print "</a></li>";
          }
        ?>
        </ul>
      </div>
    </div>
  </div>

      <!-- FOOTER -->
      <footer>
        <p class="pull-right"><a href="#">Back to top</a></p>
        <p>&copy; 2013 Company, Inc. &middot; <a href="#">Privacy</a> &middot; <a href="#">Terms</a></p>
      </footer>

    </div><!-- /.container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="./assets/js/jquery.js"></script>
    <script src="./dist/js/bootstrap.js"></script>
    <script src="./assets/js/holder.js"></script>
  </body>
</html>

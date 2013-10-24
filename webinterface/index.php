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
                    <li><a href="#">Student Center</a></li>
                    <li><a href="#">CRC</a></li>
                    <li><a href="#">Barnes & Noble</a></li>
                    <!--<li class="divider"></li>
                    <li class="dropdown-header">Nav header</li>
                    <li><a href="#">Separated link</a></li>
                    <li><a href="#">One more separated link</a></li>-->
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
          
          <!--<?php
            $ts = strtotime(date("d-m-Y"));
            $start = date('m-d-Y', $ts);
            $end = date('m-d-Y', strtotime('+1 day', $ts));
            $result = db_select_events_range($start, $end);
            $i = 0;
            while ($row = $result->fetch_array(MYSQLI_ASSOC)) {
              if (!array_key_exists($row['ID'], $tasks_shown))
              {
                ?>
                <li><a href="http://gtcampusevents.com/event/<?=$row['EVENT_NUM'];?>"><?=$row['DESC'];?> on <?=$row['EVENT_NUM'];?></a></li>
                <?php
                $i++;
              }
              else
              {
                $tasks_shown[$row['ID']] = true;
              }
            }
            if ($i == 0)
            {
              ?>
              <li><a href="#"><?=l("NO_TODAY_EVENTS");?></a></li>
              <?
            }
          ?>-->

          <li>Event 1</li>
          <li>Event 2</li>
          <li>Event 3</li>
        </ul>
      </div>
      <div class="col-lg-4">
        <h2>This Week</h2>
        <ul class="nav nav-pills nav-stacked">
          <li>Event 1</li>
          <li>Event 2</li>
          <li>Event 3</li>
          <li>Event 4</li>
        </ul>
      </div>
      <div class="col-lg-4">
        <h2>This Month</h2>
        <ul class="nav nav-pills nav-stacked">
          <li>Event 1</li>
          <li>Event 2</li>
          <li>Event 3</li>
          <li>Event 4</li>
          <li>Event 5</li>
          <li>Event 6</li>
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

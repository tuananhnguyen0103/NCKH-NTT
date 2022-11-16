(function ($) {
  "use strict";



  /*-------------------------------------
      jquery Scollup activation code
   -------------------------------------*/
  $.scrollUp({
    scrollText: '<i class="fa fa-angle-up"></i>',
    easingType: "linear",
    scrollSpeed: 900,
    animation: "fade"
  });

  /*-------------------------------------
      jquery Scollup activation code
    -------------------------------------*/
  $("#preloader").fadeOut("slow", function () {
    $(this).remove();
  });

  $(function () {
    /*-------------------------------------
          Data Table init
      -------------------------------------*/
    if ($.fn.DataTable !== undefined) {
      $('.data-table').DataTable({
        paging: true,
        searching: false,
        info: false,
        lengthChange: false,
        lengthMenu: [20, 50, 75, 100],
        columnDefs: [{
          targets: [0, -1], // column or columns numbers
          orderable: false // set orderable for selected columns
        }],
      });
    }

    /*-------------------------------------
          All Checkbox Checked
      -------------------------------------*/
    $(".checkAll").on("click", function () {
      $(this).parents('.table').find('input:checkbox').prop('checked', this.checked);
    });

    /*-------------------------------------
          Tooltip init
      -------------------------------------*/
    $('[data-toggle="tooltip"]').tooltip();

    /*-------------------------------------
          Select 2 Init
      -------------------------------------*/
    if ($.fn.select2 !== undefined) {
      $('.select2').select2({
        width: '100%'
      });
    }

    /*-------------------------------------
          Date Picker
      -------------------------------------*/
 

    /*-------------------------------------
          Counter
      -------------------------------------*/
    var counterContainer = $(".counter");
    if (counterContainer.length) {
      counterContainer.counterUp({
        delay: 50,
        time: 1000
      });
    }

    /*-------------------------------------
          Vector Map 
      -------------------------------------*/
    if ($.fn.vectorMap !== undefined) {
      $('#world-map').vectorMap({
        map: 'world_mill',
        zoomButtons: false,
        backgroundColor: 'transparent',

        regionStyle: {
          initial: {
            fill: '#0070ba'
          }
        },
        focusOn: {
          x: 0,
          y: 0,
          scale: 1
        },
        series: {
          regions: [{
            values: {
              CA: '#41dfce',
              RU: '#f50056',
              US: '#f50056',
              IT: '#f50056',
              AU: '#fbd348'
            }
          }]
        }
      });
    }

   
    /*-------------------------------------
          Calender initiate 
      -------------------------------------*/
    if ($.fn.fullCalendar !== undefined) {
      $('#fc-calender').fullCalendar({
        header: {
          center: 'basicDay,basicWeek,month',
          left: 'title',
          right: 'prev,next',
        },
        fixedWeekCount: false,
        navLinks: true, // can click day/week names to navigate views
        editable: true,
        eventLimit: true, // allow "more" link when too many events
        aspectRatio: 1.8,
        events: [{
            title: 'All Day Event',
            start: '2019-04-01'
          },

          {
            title: 'Meeting',
            start: '2019-04-12T14:30:00'
          },
          {
            title: 'Happy Hour',
            start: '2019-04-15T17:30:00'
          },
          {
            title: 'Birthday Party',
            start: '2019-04-20T07:00:00'
          }
        ]
      });
    }
  });

})(jQuery);
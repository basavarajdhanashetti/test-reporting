type = ['','info','success','warning','danger'];
    	

demo = {
    initPickColor: function(){
        $('.pick-class-label').click(function(){
            var new_class = $(this).attr('new-class');  
            var old_class = $('#display-buttons').attr('data-class');
            var display_div = $('#display-buttons');
            if(display_div.length) {
            var display_buttons = display_div.find('.btn');
            display_buttons.removeClass(old_class);
            display_buttons.addClass(new_class);
            display_div.attr('data-class', new_class);
            }
        });
    },
    
    initChartist: function(){    
        
        $.ajax({
    		   url: 'sale/year',
    		   
    		   error: function(err) {
    		     alert('Error:'+ JSON.stringify(err))
    		   },
    		   success: function(data) {
    			  
    			   		var options = {
    			            seriesBarDistance: 10,
    			            axisX: {
    			                showGrid: false
    			            },
    			            height: "265px"
    			        };
    			        
    			        var responsiveOptions = [
    			          ['screen and (max-width: 640px)', {
    			            seriesBarDistance: 5,
    			            axisX: {
    			              labelInterpolationFnc: function (value) {
    			                return value[0];
    			              }
    			            }
    			          }]
    			        ];
    			        
    			        Chartist.Bar('#chartActivity', data, options, responsiveOptions);
    		   },
    		   type: 'GET'
    		});
    	
        
        
        $.ajax({
 		   url: 'sale/product',
 		   
 		   error: function(err) {
 		     alert('Error:'+ JSON.stringify(err))
 		   },
 		   success: function(data) {
 			  
 			        var optionsPreferences = {
 			            donut: true,
 			            donutWidth: 40,
 			            startAngle: 0,
 			            total: 100,
 			            showLabel: false,
 			            axisX: {
 			                showGrid: false
 			            }
 			        };
 			    
 			       // Chartist.Pie('#chartPreferences', dataPreferences, optionsPreferences);
 			        
 			       Chartist.Pie('#chartPreferences', data); 
 		   },
 		   type: 'GET'
 		});       
    
         
    },
    
    
    
	showNotification: function(from, align){
    	color = Math.floor((Math.random() * 4) + 1);
    	
    	$.notify({
        	icon: "pe-7s-gift",
        	message: "Welcome to <b>Light Bootstrap Dashboard</b> - a beautiful freebie for every web developer."
        	
        },{
            type: type[color],
            timer: 4000,
            placement: {
                from: from,
                align: align
            }
        });
	}

    
}


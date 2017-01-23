(function($){
    "use strict";

/* -----------------------------------------------------------------------------

	PLUGINS

----------------------------------------------------------------------------- */

	/* -------------------------------------------------------------------------
		ACCORDION
	------------------------------------------------------------------------- */

	$.fn.lvAccordion = function(){

		var $this = $(this),
		isToggle = $this.hasClass( 'm-toggle' ) ? true : false,
		items = $this.find( '> li' );
		items.filter( '.m-active' ).find( '.accordion-content' ).slideDown( 300 );

		$this.find( '.accordion-title' ).click(function(){
			if ( ! $(this).parent().hasClass( 'm-active' ) ) {
				if ( ! isToggle ) {
					items.filter( '.m-active' ).find( '.accordion-content' ).slideUp(300);
					items.filter( '.m-active' ).removeClass( 'm-active' );
				}
				$(this).parent().find( '.accordion-content' ).slideDown(300);
				$(this).parent().addClass( 'm-active' );
			}
			else {
				$(this).parent().find( '.accordion-content' ).slideUp(300);
				$(this).parent().removeClass( 'm-active' );
			}
		});

		// RADIO GROUP
		if ( $this.hasClass( 'm-radio-group' ) ) {
			items.removeClass( 'm-active' );
			$this.find( '.accordion-content' ).hide();
			$this.find( 'input[type="radio"]:checked' ).parent().addClass( 'm-active' ).find( '.accordion-content' ).slideDown( 300 );
		}

	};

	/* -------------------------------------------------------------------------
		AJAX FORM
	------------------------------------------------------------------------- */

	$.fn.lvAjaxForm = function(){

		var form = $(this),
		submitBtn = form.find( '.submit-btn' );

		form.submit(function(e){
			e.preventDefault();

			if ( ! submitBtn.hasClass( 'm-loading' ) ) {

				// CLEAN OLD MESSAGES
				form.find( '.c-alert-message.m-success, .c-alert-message.m-phpvalidation-error' ).slideUp( 300, function(){
					$(this).remove();
				});

				// FORM NOT VALID
				if ( ! form.lvIsFormValid() ) {
					form.find( 'p.c-alert-message.m-warning.m-validation-error' ).slideDown(300);
					return false;
				}
				// FORM VALID
				else {

					submitBtn.addClass( 'm-loading' ).attr( 'data-label', submitBtn.text() ).text( submitBtn.data( 'loading-label' ) );

					// AJAX REQUEST
					$.ajax({
						type: 'POST',
						url: form.attr( 'action' ),
						data: form.serialize(),
						success: function( data ){

							form.find( '.c-alert-message.m-validation-error' ).hide();
							form.prepend( data );
							form.find( '.c-alert-message.m-success, .c-alert-message.m-phpvalidation-error' ).slideDown(300);
							submitBtn.removeClass( 'm-loading' ).text( submitBtn.attr( 'data-label' ) );

							// RESET ALL INPUTS
							if ( data.indexOf( 'success' ) > 0 ) {
								form.find( 'input, textarea' ).each( function() {
									$(this).val( '' );
								});
							}

						},
						error: function(){
							form.find( '.c-alert-message.m-validation-error' ).slideUp(300);
							form.find( '.c-alert-message.m-request-error' ).slideDown(300);
							submitBtn.removeClass( 'm-loading' ).text( submitBtn.attr( 'data-label' ) );
						}
					});

				}

			}
		});

	};

	/* -------------------------------------------------------------------------
		AJAX MODAL
	------------------------------------------------------------------------- */

	$.fn.lvOpenAjaxModal = function(){

		var $this = $(this),
		url = $this.attr( 'href' );

		$this.click(function(){

			// CLOSE MODAL FUNCTION
			var lvCloseAjaxModal = function(){
				$( '.c-modal' ).fadeOut( 300, function(){
					$( '.c-modal .modal-loading' ).show();
					$( '.c-modal .modal-box' ).removeClass( 'animated fadeInDown' ).unbind( 'clickoutside' ).hide();
					$( 'html' ).removeClass( 'm-modal-active' );
				});
				// TRIGGER modalClosed EVENT
				$.event.trigger({
					type: 'modalClosed',
					message: 'Modal is closed.',
					time: new Date()
				});

			};

			// CREATE / SHOW MODAL
			if ( $( '.c-modal' ).length < 1 ) {
				var modalHtml = '<div class="c-modal" style="display: none;">';
				modalHtml += '<div class="modal-loading"><span class="c-loading-anim"><span></span></span></div>';
				modalHtml += '<div class="modal-box" style="display: none;"><button class="modal-close" type="button"><i class="fa fa-times"></i></button><div class="modal-box-inner"></div></div>';
				modalHtml += '</div>';
				$( 'body' ).append( modalHtml );
			}
			var modal = $( '.c-modal' ),
			modalLoading = $( '.c-modal .modal-loading' ),
			modalBox = $( '.c-modal .modal-box' ),
			modalBoxInner = $( '.c-modal .modal-box-inner' ),
			modalClose = $( '.c-modal .modal-close' );
			modal.fadeIn( 300 );
			$( 'html' ).addClass( 'm-modal-active' );

			// LOAD CONTENT
			modalBoxInner.load( url, function( response, status, xhr ){

				// SHOW MODAL BOX
				modalLoading.fadeOut( 300 );
				modalBox.show().addClass( 'animated fadeInDown' );

				// CLICK ON CLOSE
				modalClose.click(function(){
					lvCloseAjaxModal();
				});

				// iOS & Chrome BODY SCROLLING FIX
				$( 'body' ).on( 'touchmove', function (e) {
					if ( $( '.c-modal' ).is( ':visible' ) ) {
						if ( ! $( '.c-modal' ).has( $( e.target ) ).length ) {
							e.preventDefault();
						}
					}
				});
				$( 'body' ).on( 'mousewheel DOMMouseScroll', function (e) {
					if ( $( '.c-modal' ).is( ':visible' ) ) {
						if ( ! $( '.c-modal' ).has( $( e.target ) ).length ) {
							e.preventDefault();
						}
					}
				});

				// CLICK OUTSIDE
				/*
				modalBox.unbind( 'clickoutside' ).bind( 'clickoutside', function(){
					lvCloseAjaxModal();
				});
				*/

				// ERROR LOADING CONTENT
				if ( status == 'error' ) {
					modalBoxInner.html( '' ).append( '<p class="c-alert-message m-warning"><i class="ico fa fa-exclamation-circle"></i> Failed to load reservation form, please try again later.</p>' );
				}

				// INIT AJAX FORMS
				else {

					if ( $.fn.lvAjaxForm ) {
						$( 'form.m-ajax-form' ).each(function(){
							$(this).lvAjaxForm();
						});
					}

				}

				// TRIGGER modalOpened EVENT
				$.event.trigger({
					type: 'modalOpened',
					message: 'Modal is opened.',
					time: new Date()
				});

			});

			// DISABLE DEFAULT CLICK ACTION
			return false;

		});

	};

	/* -------------------------------------------------------------------------
		DATEPICKER INPUT
	------------------------------------------------------------------------- */

	$.fn.lvDatepickerInput = function(){
		if ( $.fn.datepicker ) {

			var $self = $(this),
			input = $self.find( 'input' ),
			ico = $self.find( 'i.fa' ),
			firstDay = $self.data( 'first-day' ) ? $self.data( 'first-day' ) : 0,
			dateFormat = $self.data( 'date-format' ) ? $self.data( 'date-format' ) : 'mm/dd/yy';

			// INIT
			input.datepicker({
				dateFormat: dateFormat,
				minDate: -0,
				firstDay: firstDay,
				//beforeShowDay: $.datepicker.noWeekends, // disable weekends
				beforeShow: function( input, inst ){
					$( '#ui-datepicker-div' ).appendTo( $self );
				}
			});
			ico.click(function(){
				input.focus();
			});

		}
	};

	/* -------------------------------------------------------------------------
		FLUID VIDEOS
	------------------------------------------------------------------------- */

	$.fn.lvFluidEmbedVideo = function(){

		var $self = $(this),
		allVideos;

        var reloadFluidVideos = function(){
			// Resize all videos according to their own aspect ratio
            allVideos.each(function() {
                var el = $(this);
                var elContainer = el.parents( '.embed-video' );
                var newWidth = elContainer.width();
                el.width( newWidth ).height( newWidth * el.data( 'aspectRatio' ) );
            });
        };

        var generateFluidVideos = function(){
            // Find all videos
            allVideos = $self.find( '.embed-video iframe' );
            // The element that is fluid width
            //$fluidEl = $('.embed-video').first();
            // Figure out and save aspect ratio for each video
            allVideos.each(function() {
                $(this).data( 'aspectRatio', this.height / this.width )
                    // and remove the hard coded width/height
                    .removeAttr( 'height' )
                    .removeAttr( 'width' );
            });
            reloadFluidVideos();
        };

		if ( $self.find( '.embed-video' ).length > 0 ) {
			generateFluidVideos();
			$(window).resize(function(){
				reloadFluidVideos();
			});
		}

	};

	/* -------------------------------------------------------------------------
		FORM VALIDATION
	------------------------------------------------------------------------- */

	$.fn.lvIsFormValid = function() {

		function emailValid( email ) {
			var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			return re.test(email);
		}

		// TRIM FIX FOR IE
		if ( typeof String.prototype.trim !== 'function' ) {
			String.prototype.trim = function() {
				return this.replace(/^\s+|\s+$/g, '');
			}
		}

		var form = $(this),
		formValid = true;

		// VALIDATE FIELD
		$.fn.lvIsFieldValid = function(){

			var field = $(this),
			value = field.val(),
			placeholder = field.data( 'placeholder' ) ? field.data( 'placeholder' ) : false,
			valid = false;

			if ( value.trim() !== '' && ! ( placeholder && value === placeholder ) ) {

				// EMAIL FIELDS
				if ( field.hasClass( 'm-email' ) ) {
					if ( ! emailValid( value ) ) {
						field.addClass( 'm-error' );
					}
					else {
						field.removeClass( 'm-error' );
						valid = true;
					}
				}

				// SELECT FIELD
				else if ( field.prop( 'tagName' ).toLowerCase() === 'select' ) {
					if ( value === null ) {
						field.addClass( 'm-error' );
					}
					else {
						field.removeClass( 'm-error' );
						valid = true;
					}
				}

				// DEFAULT FIELD
				else {
					field.removeClass( 'm-error' );
					valid = true;
				}

			}
			else {
				field.addClass( 'm-error' );
			}

			return valid;

		};

		// CHECK REQUIRED FIELDS
		form.find( 'input.m-required, textarea.m-required, select.m-required' ).each(function(){
			formValid = ! $(this).lvIsFieldValid() ? false : formValid;
		});

		// CHECK REQUIRED ONE FIELDS
		var requireOneValid = false;
		form.find( 'input.m-required-one, textarea.m-required-one, select.m-required-one' ).each(function(){
			if ( $(this).lvIsFieldValid() ) {
				requireOneValid = true;
				form.find( 'input.m-required-one, textarea.m-required-one, select.m-required-one' ).removeClass( 'm-error' );
			}
		});
		if ( form.find( '.m-require-one' ).length > 0 && ! requireOneValid ) {
			formValid = false;
		}
		if ( formValid ) {
			form.find( 'input.m-required-one, textarea.m-required-one, select.m-required-one' ).removeClass( 'm-error' );
		}

		form.find( '.m-error' ).first().focus();

		return formValid;

	};

	/* -------------------------------------------------------------------------
		CHECKBOX INPUT
	------------------------------------------------------------------------- */

	$.fn.lvCheckboxInput = function(){

		var $this = $(this);
		$this.wrap( '<div class="checkbox-input"></div>' );
		$this = $this.parent();
		var input = $this.find( 'input' ).removeClass( 'checkbox-input' ),
		label = $this.next( 'label' ).appendTo( $this );

		// INIT
		if ( input.is( ':checked' ) ) {
			$this.addClass( 'm-checked' );
		}

		// CLICK
		label.click(function(){

			// RADIO
			if ( input.attr( 'type' ) === 'radio' && input.attr( 'name' ) && input.attr( 'name' ) !== '' ) {

				$( 'input[name="' + input.attr( 'name' ) + '"]' ).each(function(){
					$(this).parents( '.checkbox-input' ).removeClass( 'm-checked' );
				});
				$this.addClass( 'm-checked' );

			}
			// CHECKBOX
			else {
				$this.toggleClass( 'm-checked' );
				input.trigger( 'change' );
			}

		});

	};

	/* -------------------------------------------------------------------------
		LIGHTBOX
	------------------------------------------------------------------------- */

	// LIGHTBOX SETUP
	if ( $.fn.magnificPopup ) {
		$.extend( true, $.magnificPopup.defaults, {
			tClose: 'Close (Esc)',
			tLoading: 'Loading...',
			gallery: {
				tPrev: 'Previous (Left arrow key)', // Alt text on left arrow
				tNext: 'Next (Right arrow key)', // Alt text on right arrow
				tCounter: '%curr% / %total%' // Markup for "1 of 7" counter
			},
			image: {
				tError: '<a href="%url%">The image</a> could not be loaded.' // Error message when image could not be loaded
			},
			ajax: {
				tError: '<a href="%url%">The content</a> could not be loaded.' // Error message when ajax request failed
			}
		});
	}

	// FUNCTION
	$.fn.lvInitLightboxes = function(){
		if ( $.fn.magnificPopup ) {
			$(this).find( 'a.lightbox' ).each(function(){

				var self = $(this),
				lightboxGroup = self.data( 'lightbox-group' ) ? self.data( 'lightbox-group' ) : false;

				// SHOW AS GALLERY
				if ( lightboxGroup ) {
					$( 'a.lightbox[data-lightbox-group="' + lightboxGroup + '"]' ).magnificPopup({
						type: 'image',
						removalDelay: 300,
						mainClass: 'mfp-fade',
						gallery: {
							enabled: true
						}
					});
				}
				// SHOW SINGLE IMAGE
				else {
					self.magnificPopup({
						type: 'image'
					});
				}

			});
		}
	};

	/* -------------------------------------------------------------------------
		LOAD HIRES IMAGES
	------------------------------------------------------------------------- */

	$.fn.lvLoadHiresImages = function() {
		if ( window.devicePixelRatio > 1 ) {
			$(this).find( 'img[data-hires]' ).each(function(){
				$(this).attr( 'src', $(this).data( 'hires' ) );
			});
		}
	};

	/* -------------------------------------------------------------------------
		MAILCHIMP SUBSCRIBE FORM
	------------------------------------------------------------------------- */

	$.fn.lvMailchimpSubscribeForm = function(){

		var form = $(this),
		submitBtn = form.find( '.submit-btn' );

		form.submit(function(e){
			e.preventDefault();
			if ( ! form.hasClass( 'm-loading' ) ) {

				// FORM IS VALID
				if ( form.lvIsFormValid() ) {

					form.find( 'p.c-alert-message.m-warning.m-validation-error' ).slideUp(300);
					form.addClass( 'm-loading' );
					submitBtn.attr( 'data-label', submitBtn.text() ).text( submitBtn.data( 'loading-label' ) );

					// SEND AJAX REQUEST
                    $.ajax({
                        type: form.attr( 'method' ),
                        url: form.attr( 'action' ),
                        data: form.serialize(),
                        cache : false,
                        dataType : 'json',
                        contentType: "application/json; charset=utf-8",
                        // WAIT FOR RESPONSE
                        success: function( data ){

                            if ( data.result === 'success' ) {
                                form.find( '.c-alert-message' ).hide();
                                form.find( '.c-alert-message.m-success' ).append( '<br>' + data.msg ).slideDown(300);
                                form.find( '.form-fields' ).slideUp(300);
                            }
                            else {
                                form.find( '.c-alert-message.m-validation-error' ).slideUp(300);
                                form.find( '.c-alert-message.m-request-error' ).slideDown(300);
                            }

							form.removeClass( 'm-loading' );
							submitBtn.text( submitBtn.attr( 'data-label' ) );

                        },
                        error: function(){

                            form.find( '.m-alert-message.m-validation-error' ).slideUp(300);
                            form.find( '.m-alert-message.m-request-error' ).slideDown(300);
                            form.removeClass( 'loading' );
							submitBtn.text( submitBtn.attr( 'data-label' ) );

                        }
                    });

				}

				//  FORM IS INVALID
				else {
					form.find( 'p.c-alert-message.m-warning.m-validation-error' ).slideDown(300);
					return false;
				}

			}
		});

	};

	/* -------------------------------------------------------------------------
		MEDIA QUERY BREAKPOINT
	------------------------------------------------------------------------- */

	$.fn.lvGetMediaQueryBreakpoint = function() {

		if ( $( '#media-query-breakpoint' ).length < 1 ) {
			$( 'body' ).append( '<span id="media-query-breakpoint" style="display: none;"></span>' );
		}
		var value = $( '#media-query-breakpoint' ).css( 'font-family' );
		if ( typeof value !== 'undefined' ) {
			value = value.replace( "\"", "" ).replace( "\"", "" ).replace( "\'", "" ).replace( "\'", "" );
		}
		if ( isNaN( value ) ) {
			return $(window).width();
		}
		else {
			return parseInt( value );
		}

	};

	/* -------------------------------------------------------------------------
		PROGRESS BAR
	------------------------------------------------------------------------- */

	$.fn.lvProgressBar = function(){

		var $this = $(this),
		percentage = $this.data( 'percentage' ) ? parseInt( $this.data( 'percentage' ) ) : 100,
		inner = $this.find( '> span' );
		inner.css( 'width', percentage + '%' );

	};

	/* -------------------------------------------------------------------------
		QUANTITY INPUT
	------------------------------------------------------------------------- */

	$.fn.lvQuantityInput = function(){

		var $this = $(this),
		input = $this.find( 'input' ),
		value = parseInt( input.val() ),
		minValue = input.prop( 'min' ) ? parseInt( input.prop( 'min' ) ) : 0,
		maxValue = input.prop( 'max' ) ? parseInt( input.prop( 'max' ) ) : 100,
		step = input.prop( 'step' ) ? parseInt( input.prop( 'step' ) ) : 1;

		// CREATE ELEMENTS
		input.hide();
		$this.append( '<input class="fake-input" type="text" value="' + value + '"><button class="minus" type="button"><i class="fa fa-minus"></i></button><button class="plus" type="button"><i class="fa fa-plus"></i></button>' );
		var plus = $this.find( '.plus' ),
		minus = $this.find( '.minus' ),
		fakeInput = $this.find( '.fake-input' );
		if ( input.hasClass( 'm-type-2' ) ){
			fakeInput.addClass( 'm-type-2' );
		}

		// ACTIONS
		minus.click(function(){
			if ( ( value - step ) >= minValue ) {
				value -= step;
				fakeInput.val( value );
				fakeInput.trigger( 'change' );
			}
		});
		plus.click(function(){
			if ( ( value + step ) <= maxValue ) {
				value += step;
				fakeInput.val( value );
				fakeInput.trigger( 'change' );
			}
		});
		fakeInput.change(function(){
			if ( ! isNaN( fakeInput.val() ) ) {
				value = parseInt( fakeInput.val() );
				input.val( value );
			}
		});

	};

	/* -------------------------------------------------------------------------
		SELECTBOX INPUT
	------------------------------------------------------------------------- */

	$.fn.lvSelectboxInput = function(){

		var $this = $(this);
		$this.wrap( '<div class="selectbox-input"></div>' );
		$this = $this.parent();
		var input = $this.find( 'select' ),
		fakeSelectHtml = '';
		input.removeClass( 'selectbox-input' );
		var value = input.val();
		var defaultValue = input.find( 'option[value="' + value + '"]' ).text() ? input.find( 'option[value="' + value + '"]' ).text() : input.find( 'option' ).first().text();

		// COPY CLASSES
		if ( input.hasClass( 'm-small' ) ) {
			$this.addClass( 'm-small' );
		}
		if ( input.hasClass( 'm-type-2' ) ) {
			$this.addClass( 'm-type-2' );
		}

		// CREATE ELEMENTS
		input.hide();
		$this.append( '<button type="button" class="toggle"><span>' + defaultValue + '</span></button>' );
		fakeSelectHtml = '<ul class="fake-selectbox" style="display: none;">';
		input.find( 'option' ).each(function(){
			fakeSelectHtml += '<li data-value="' + $(this).attr( 'value' ) + '">' + $(this).text() + '</li>';
		});
		fakeSelectHtml += '</ul>';
		$this.append( fakeSelectHtml );
		var toggle = $this.find( '.toggle' ),
		fakeSelect = $this.find( '.fake-selectbox' );

		// TOGGLE
		toggle.click(function(){
			fakeSelect.slideToggle(150);
			toggle.toggleClass( 'm-active' );
			$this.unbind( 'clickoutside' );
			if ( toggle.hasClass( 'm-active' ) ) {
				$this.bind( 'clickoutside', function(event){
					fakeSelect.slideUp(150);
					toggle.removeClass( 'm-active' );
					$this.unbind( 'clickoutside' );
				});
			}
		});

		// FAKE SELECTBOX CLICK
		fakeSelect.find( 'li' ).each(function(){
			$(this).click(function(){
				toggle.removeClass( 'm-active' ).find( 'span' ).text( $(this).text() );
				fakeSelect.slideUp(150);
				input.val( $(this).attr( 'data-value' ) );
				input.trigger( 'change' );
			});
		});

	};

	/* -------------------------------------------------------------------------
		TABS
	------------------------------------------------------------------------- */

	$.fn.lvTabbed = function(){

		var $this = $(this),
		tabs = $this.find( '.tab-list > li' ),
		contents = $this.find( '.content-list > li' );

		tabs.click(function(){
			if ( ! $(this).hasClass( 'm-active' ) ) {
				var index = $(this).index();
				tabs.filter( '.m-active' ).removeClass( 'm-active' );
				$(this).addClass( 'm-active' );
				contents.filter( ':visible' ).slideUp( 300, function(){
					$(this).removeClass( 'm-active' );
				});
				contents.filter( ':eq(' + index + ')' ).slideDown(300).addClass( 'm-active' );
			}
		});

	};

	/* -------------------------------------------------------------------------
		TWITTER FEED
	------------------------------------------------------------------------- */

	$.fn.lvTwitterFeed = function(){
		if ( $.fn.tweet ) {

			var $this = $(this),
			feedId = $this.data( 'id' ),
			feedLimit = parseInt( $this.data( 'limit' ) ),
			feedEl = $this.find( '.twitter-feed' );

			// TWEETS LOADED
			feedEl.bind( 'loaded', function(){
				if ( feedLimit > 1 && $this.hasClass( 'm-paginated' ) && $.fn.owlCarousel ) {

					var interval = $this.data( 'interval' ) ? parseInt( $this.data( 'interval' ) ) > 0 : false;
					$this.find( '.tweet_list' ).owlCarousel({
						autoPlay: interval,
						slideSpeed: 300,
						pagination: false,
						paginationSpeed : 400,
						singleItem: true,
						autoHeight: true
					});
					var carousel = $this.find( '.tweet_list' ).data( 'owlCarousel' );
					$this.append( '<button class="btn-prev" type="button"><i class="fa fa-chevron-left"></i></button><button class="btn-next" type="button"><i class="fa fa-chevron-right"></i></button>' );

					// PREV TWEET
					$this.find( '.btn-prev' ).click(function(){
						$this.find( '.tweet_list' ).trigger( 'owl.prev' );
					});

					// NEXT TWEET
					$this.find( '.btn-next' ).click(function(){
						$this.find( '.tweet_list' ).trigger( 'owl.next' );
					});

				}
			});

			// INIT TWEET JS
			feedEl.tweet({
				username: feedId,
				modpath: './library/twitter/',
				count: feedLimit,
				loading_text: '<span class="c-loading-anim"><span></span></span>'
			});

		}
	};


/* -----------------------------------------------------------------------------

	EVENTS

----------------------------------------------------------------------------- */

	/* -------------------------------------------------------------------------
		SCREEN SIZE TRANSITION
	------------------------------------------------------------------------- */

	if ( $.fn.lvGetMediaQueryBreakpoint ) {
		var mediaQueryBreakpoint = $.fn.lvGetMediaQueryBreakpoint();
		$(window).resize(function(){
			if ( $.fn.lvGetMediaQueryBreakpoint() !== mediaQueryBreakpoint ) {
				mediaQueryBreakpoint = $.fn.lvGetMediaQueryBreakpoint();
				$.event.trigger({
					type: 'screenTransition',
					message: 'Screen transition completed.',
					time: new Date()
				});
			}
		});
	}

})(jQuery);
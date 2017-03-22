$(document).ready(function() {
	$('#register-as').change(function() {
		var selected = $(this).find(":selected").val();
		if(selected === 'candidate') {
			$('.candidate-attribute').toggle();
			$('.employee-attribute').toggle();
		} else if (selected === 'employee') {
			$('.candidate-attribute').toggle();
			$('.employee-attribute').toggle();
		}
	});
});
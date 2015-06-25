var personId;
$(function() {
	var params = {};
	$('#p').click(function() {
		displayPersons('/getSqlDataPerson', params);
	});

	$('#b').click(function() {
		displayBooks('/getSqlDataBook', params);
	});
});
function displayPersons(url, params) {
	$.get(url, params).done(function(data) {
		var html = '';
		$('#message').html(data.msg);

		$.get('/persons.html', function(rez) {
			var rendered = Mustache.render(rez, {
				persons : data
			});
			$('#person').html(rendered);
		});
	}).fail(function(data) {
		$('#info').html('Error!');

	});
}

function displayBooks(url, params) {
	$.get(url, '/getSqlDataBook', params).done(function(data) {
		var html = '';
		$('#message').html(data.msg);

		$.get('/books.html', function(rez) {
			var rendered = Mustache.render(rez, {
				books : data
			});
			$('#book').html(rendered);
		});
	}).fail(function(data) {
		$('#info').html('Error!');

	});
}

$('#save').click(function() {

	var name = $('#name').val();
	var age = $('#age').val();
	var city = $('#city').val();

	var params = {
		'name' : name,
		age : age,
		'city' : city,
	};

	displayPersons('/add', params);
	$('#formPerson').hide();
	$('#tablePerson').show();
});

function add() {
	$('#tablePerson').hide();
	$('.searchForm').hide();
	$('#formPerson').show();
	$('#save').show();
	$('#rename').hide();

}

function del(id) {
	var params = {
		id : id
	};

	displayPersons('/delete', params);
	$('#formPerson').hide();
}

function editPerson(id) {
	var params = {
		id : id,
	};

	personId = id;

	$.get('/getPerson', params, function(person) {
		$(".fa-spin").hide();
		$(".title").show();
		$('#name').val(person.name);
		$('#age').val(person.age);
		$('#city').val(person.city);
		$('#formPerson').show();
		$('#rename').show();
	});

	$(".fa-spin").show();
	$(".title").hide();
	$('#save').hide();
	$('#tablePerson').hide();
	$('.searchForm').hide();
}

$('#rename').click(function() {
	var name = $('#name').val();
	var age = $('#age').val();
	var city = $('#city').val();
	var params = {
		'name' : name,
		age : age,
		id : personId,
		'city' : city
	};

	$('#name').val('');
	$('#age').val('');
	$('#city').val('');
	displayPersons('/editPerson', params);
	$('#tablePerson').show();
	$('#formPerson').hide();
	$('#home').hide();
});

$('#searchPerson').click(function() {
	var val = $('#searchVal').val();
	var params = {
		'val' : val
	};

	displayPersons('/searchPerson', params);
	$('#addPerson').hide();
	$('#back2').show();
});

$('#back2').click(function() {
	$('#searchVal').val('');
	var params = {};
	displayPersons('/getSqlDataPerson', params);
	$('#addPerson').show();
	$('#back2').hide();

});

$('#back').click(function() {
	$('#formPerson').hide();
	$('#tablePerson').show();
});

$("#formPerson").hide();
$("#back2").hide();
$(".fa-spin").hide();
$('#tablePerson').hide();
$('.searchForm').hide();
$('#save').show();
$('#rename').hide();
$('#addBook').hide();
$('#addPerson').hide();
$('#back2Book').hide();
$('#home').css("background-color", "#f0ad4e");



$('#p').click(function() {
	$(this).css("background-color", "#f0ad4e");
	$('#b').css("background-color", "#1BA3B5");
	$('#home').css("background-color", "#1BA3B5");
	$('#tableBook').hide();
	$('#tablePerson').show();
	$('.searchForm').show();
	$('#homeImg').hide();
	$('#addPerson').show();

});

$('#b').click(function() {
	$(this).css("background-color", "#f0ad4e");
	$('#p').css("background-color", "#1BA3B5");
	$('#home').css("background-color", "#1BA3B5");
	$('#tableBook').show();
	$('#tablePerson').hide();
	$('.searchForm').show();
	$('#homeImg').hide();
	$('#addBook').show();

});
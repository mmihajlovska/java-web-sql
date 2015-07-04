var personId;
var bookId;

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
	$.get(url, params).done(function(data) {
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

$('#savePerson').click(function() {
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

$('#saveBook').click(function() {
	var title = $('#title').val();
	var year = $('#year').val();

	var params = {
		'title' : title,
		year : year,
	};

	displayBooks('/addBook', params);
	$('#formBook').hide();
	$('#tableBook').show();
});

function add() {
	$('#tablePerson').hide();
	$('.searchForm').hide();
	$('#formPerson').show();
	$('#savePerson').show();
	$('#renamePerson').hide();

}

function addBook() {
	$('#tableBook').hide();
	$('.searchFormBook').hide();
	$('#formBook').show();
	$('#saveBook').show();
	$('#renameBook').hide();

}

function del(id) {
	var params = {
		id : id
	};

	displayPersons('/delete', params);
	$('#formPerson').hide();
}

function delBook(id) {
	var params = {
		id : id
	};

	displayBooks('/deleteBook', params);
	$('#formBook').hide();
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
		$('#renamePerson').show();
	});

	$(".fa-spin").show();
	$(".title").hide();
	$('#savePerson').hide();
	$('#tablePerson').hide();
	$('.searchForm').hide();
}

function editBook(id) {
	var params = {
		id : id,
	};

	bookId = id;

	$.get('/getBook', params, function(book) {
		$(".fa-spin").hide();
		$(".title").show();
		$('#title').val(book.title);
		$('#year').val(book.year);
		$('#formBook').show();
		$('#renameBook').show();
	});

	$(".fa-spin").show();
	$(".title").hide();
	$('#saveBook').hide();
	$('#tableBook').hide();
	$('.searchForm').hide();
}

$('#renamePerson').click(function() {
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

$('#renameBook').click(function() {
	var title = $('#title').val();
	var year = $('#year').val();
	var params = {
		'title' : title,
		year : year,
		id : bookId,
	};

	$('#title').val('');
	$('#year').val('');
	displayBooks('/editBook', params);
	$('#tableBook').show();
	$('#formBook').hide();
	$('#home').hide();
});

$('#searchPerson').click(function() {
	var val = $('#searchVal').val();
	var params = {
		'val' : val
	};

	displayPersons('/searchPerson', params);
	$('#addPerson').hide();
	$('#searchBackPerson').show();
});

$('#searchBook').click(function() {
	var val = $('#searchValBook').val();
	var params = {
		'val' : val
	};

	displayBooks('/searchBook', params);
	$('#addBook').hide();
	$('#searchBackBook').show();
});

$('#searchBackPerson').click(function() {
	$('#searchVal').val('');
	var params = {};
	displayPersons('/getSqlDataPerson', params);
	$('#addPerson').show();
	$('#searchBackPerson').hide();

});
$('#searchBackBook').click(function() {
	$('#searchValBook').val('');
	var params = {};
	displayBooks('/getSqlDataBook', params);
	$('#addBook').show();
	$('#searchBackBook').hide();

});

$('#addBackPerson').click(function() {
	$('#formPerson').hide();
	$('#tablePerson').show();
	$('.searchForm').show();
});

$('#addBackBook').click(function() {
	$('#formBook').hide();
	$('#tableBook').show();
	$('.searchFormBook').show();
});


$("#formPerson").hide();
$("#formBook").hide();
$("#searchBackPerson").hide();
$(".fa-spin").hide();
$('#tablePerson').hide();
$('.searchForm').hide();
$('.searchFormBook').hide();
$('#savePerson').show();
$('#renamePerson').hide();
$('#addBook').hide();
$('#addPerson').hide();
$('#searchBackBook').hide();
$('#home').css("background-color", "#f0ad4e");

$('#p').click(function() {
	$(this).css("background-color", "#f0ad4e");
	$('#b').css("background-color", "#1BA3B5");
	$('#home').css("background-color", "#1BA3B5");
	$('#tableBook').hide();
	$('#tablePerson').show();
	$('.searchForm').show();
	$('.searchFormBook').hide();
	$('#homeImg').hide();
	$('#addPerson').show();

});

$('#b').click(function() {
	$(this).css("background-color", "#f0ad4e");
	$('#p').css("background-color", "#1BA3B5");
	$('#home').css("background-color", "#1BA3B5");
	$('#tableBook').show();
	$('#tablePerson').hide();
	$('.searchForm').hide();
	$('.searchFormBook').show();
	$('#homeImg').hide();
	$('#addBook').show();

});
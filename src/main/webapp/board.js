var columns = new Array('A','B','C','D','E','F','G','H');
var pieces;
var NO_OP = 0;
var GET_MOVES = 1;
var MOVE = 2;
var UPDATE = 3;
var currentRequestType = NO_OP;
var req = false;
var lastGridClicked = "";
var updateXml = false;
var isGameOver = false;

function makeRequest(url, requestType) {
	currentRequestType = requestType;
	req = false;
    // branch for native XMLHttpRequest object
    if(window.XMLHttpRequest) {
    	try {
			req = new XMLHttpRequest();
        } catch(e) {
			req = false;
        }
    // branch for IE/Windows ActiveX version
    } else if(window.ActiveXObject) {
       	try {
        	req = new ActiveXObject("Msxml2.XMLHTTP");
      	} catch(e) {
        	try {
          		req = new ActiveXObject("Microsoft.XMLHTTP");
        	} catch(e) {
          		req = false;
        	}
		}
    }
	if(req) {
		req.onreadystatechange = processReqChange;
		req.open("GET", url, true);
		req.send("");
	}
}

function processReqChange() {
    // only if req shows "loaded"
    if (req.readyState == 4) {
        // only if "OK"
        if (req.status == 200) {
        	if (currentRequestType == GET_MOVES) {
        		markMoves(req.responseXML);
        	} else if (currentRequestType == MOVE) {
        		movePiece(req.responseXML);
        	} else if (currentRequestType == UPDATE) {
        		updateXml = req.responseXML;
        		if (updateXml.getElementsByTagName("newInformation")[0].attributes[0].value == "true") {
	        		clearAllPieces();
	        		setupBoard();
        		}
        	}
        } else {
        	clearAllSelected();
        }
    }
}

function movePiece(moveXml) {
	if (moveXml) {
		clearAllSelected();
		move = moveXml.getElementsByTagName("move")[0];
		
		if (move.attributes[0].value == "true") {
			from = move.attributes[1].value;
			to =  move.attributes[2].value;
			document.getElementById(from).innerHTML = '<img src="images/clear.gif" width="50" height="50"/>';
			putPiece(move.attributes[4].value, move.attributes[3].value.substr(0,1), to);
			showMovedPiece(from,to);
		}
						
		lastGridClicked = '';
		
		if (move.attributes[5].value != '') {
			alert(move.attributes[5].value);
		}
	}
}

function clearAllPieces() {
	for (i = 0; i < 8; i++) {
		for (j = 1; j <= 8; j++) {
			document.getElementById(columns[i]+j).innerHTML= '<img src="images/clear.gif" width="50" height="50"/>';
		}
	}
}

function clearAllSelected() {
	for (i = 0; i < 8; i++) {
		for (j = 1; j <= 8; j++) {
			var grid = document.getElementById(columns[i]+j);
			if (grid.className == "gs") {
				grid.className = "g";
			} else if (grid.className == "ws") {
				grid.className = "w";
			}
		}
	}
}

function markMoves(movesXml) {
	if (movesXml) {
		position = movesXml.getElementsByTagName("move");

		if (position.length > 0) {
			clearAllSelected();
			select(lastGridClicked);
			
			for(i = 0; i < position.length; i++) {
				select(position[i].attributes[0].value);
			}
		}
	}
}

function select(position) {
	grid = false;
	grid = document.getElementById(position);
	
	if (grid) {
		if (grid.className == "g") {
			grid.className = "gs";
		} else {
			grid.className = "ws";
		}
	}
}

function clicky(grid) {
	if (grid.id == lastGridClicked) {
		clearAllSelected();
		lastGridClicked = '';
		return;
	}
	
	if (grid.className == "ws" || grid.className == "gs") {
		makeRequest("MakeMove.do?from=" + lastGridClicked + "&to=" + grid.id + "&skipTurn=false", MOVE);	
	} else {
		makeRequest("GetMoves.do?position=" + grid.id, GET_MOVES);
	}
	
	lastGridClicked = grid.id;
}

function setupBoard() {
	if (updateXml) {
		pieces = updateXml.getElementsByTagName("piece");
		for (var i = 0; i < pieces.length; i++) {
			var color = pieces[i].attributes[1].value;
			var piece = pieces[i].attributes[0].value;
			var position = pieces[i].attributes[2].value;

			putPiece(color, piece.substr(0,1), position);				
		}

		setUserInfo();
	}
}

function setUserInfo(userColor) {
	if (updateXml) {
		user = updateXml.getElementsByTagName("player");
		color = user[0].attributes[0].value;
		username = user[0].attributes[1].value;
		document.getElementById(color + "User").innerHTML = username;
		document.getElementById(color + "Remaining").innerHTML = user[0].attributes[2].value;

		inCheckWarning = '';
		if (user[0].attributes[3].value == 'true') {
			inCheckWarning = username + ' is in check!<br/>';
		}
		
		color = user[1].attributes[0].value;
		username = user[1].attributes[1].value;
		document.getElementById(color + "User").innerHTML = user[1].attributes[1].value;
		document.getElementById(color + "Remaining").innerHTML = user[1].attributes[2].value;
		
		if (user[1].attributes[3].value == 'true') {
			inCheckWarning += username + ' is in check!<br/>';
		}
		
		turn = updateXml.getElementsByTagName("turn");
		document.getElementById("currentUser").innerHTML = "It is " + turn[0].attributes[1].value + "'s turn!";
		document.getElementById("turnsTaken").innerHTML = 'Turns Taken: ' + turn[0].attributes[0].value;

		document.getElementById("checkWarning").innerHTML = inCheckWarning;

		if (turn[0].attributes[3].value != '' && turn[0].attributes[4].value != '') {
			showMovedPiece(turn[0].attributes[3].value, turn[0].attributes[4].value);
		}
		
		if (turn[0].attributes[2].value != '') {
			isGameOver = true;
			endGameMessage(turn[0].attributes[2].value);
		}
	}
}

function endGameMessage(message) {
	document.getElementById("gameOverMessage").innerHTML = message + '<br><a href="Chessers.do">Start a new game</a>';
	document.getElementById("currentUser").innerHTML = '&nbsp;';
	document.getElementById("checkWarning").innerHTML = '&nbsp;';
	document.getElementById("skipTurn").innerHTML = '&nbsp;';
}

function animateEndGameMessage() {

}

function putPiece(color, piece, position) {
	var grid = document.getElementById(position);

	if (grid != null) {
		if (color == "black") {
			color = "B";
		} else {
			color = "W";
		}
		
		grid.innerHTML='<img src="images/' + color + piece + '.png" width="40" height="40"/>';
	}
}

function requestUpdate(){
    if (!isGameOver) {
    	if (!req || req.readyState == 4) {
        	makeRequest("RefreshGame.do?updateOnly=yes", UPDATE);
    	}
	
		setTimeout("requestUpdate()", 2000);
	}
}

var flashTicks = 0;
var flashFrom = '';
var flashTo = '';
var flashFromOriginal = '';
var flashToOriginal = '';

function showMovedPiece(from, to) {
	if (flashTicks != 0) {
		return;
	}
	
	flashTicks = 11;
	flashFrom = from;
	flashTo = to;
	flashFromOriginal = document.getElementById(from).className;
	flashToOriginal = document.getElementById(to).className;
	animateMovedPiece();
}

function animateMovedPiece() {
	flashTicks--;

	if (flashTicks % 2 == 0) {
		document.getElementById(flashTo).className='highlight';
		document.getElementById(flashFrom).className=flashFromOriginal;
	} else {
		document.getElementById(flashTo).className=flashToOriginal;
		document.getElementById(flashFrom).className='highlight';
	}
	
	if (flashTicks > 0) {
		setTimeout("animateMovedPiece()", 500);
	} else {
		document.getElementById(flashTo).className=flashToOriginal;
		document.getElementById(flashFrom).className=flashFromOriginal;
	}
}

function skipTurn() {
	makeRequest("MakeMove.do?from=A1&to=A1&skipTurn=true", MOVE);
}
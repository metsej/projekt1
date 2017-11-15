
function drawDayReportTable(resultJSON) {
	$("#records_table tr").remove();
	$("<tr> <th>Hour</th> <th>Time of work [min]</th> </tr>").appendTo('#records_table');
	$.each(resultJSON, function(key, value) {
		$('<tr>').append($('<td>').text(key), $('<td>').text(value))
			.appendTo('#records_table');
	});
}

/*--------------------------- HELPER FUNCTIONS OF drawChart() ------------------------------------*/

function getMaxValue (data) {
	var maxVal = 0;
	$.each (data , function(key, value) {
		if (value > maxVal) {
			maxVal= value;
		}
	});
	return maxVal;
}

function getMaxScaleValue(scale , m) {
	return Math.ceil(m) * scale;
}

function mFactorforVal(val, maxVal) {
	return maxVal/val;
}

function calcQulityOfResult(maxValInData, ticks) {
	var unusedSpace =
    ((ticks.maxValue - maxValInData)/ ticks.maxValue);

		return ticks.ticks  -  10 * unusedSpace;


}

function getTicks(maxValinData, mult) {
    var logVal = Math.log10(maxValinData / mult);
	var power = Math.ceil(logVal) - 1;
	var tickVal = Math.pow(10,power) * mult;
	var ticks = Math.ceil(maxValinData/tickVal);
	var maxValOnScale = tickVal * ticks;
	return { tickValue : tickVal, ticks: ticks, maxValue : maxValOnScale};
}


function getScale(maxVal) {
	if (maxVal == 0) {
		return 1;
	}
	var array = [1.0,2.0,5.0];
	var solutions = [];
	var bestIndex = 0;
    var bestQuality = Number.NEGATIVE_INFINITY;
	for (var i = 0; i < array.length; i++) {
		solutions.push(getTicks(maxVal, array[i]));
		var qualityOfCandidate = calcQulityOfResult(maxVal,solutions[i]);
		if (qualityOfCandidate > bestQuality) {
			bestQuality = qualityOfCandidate;
			bestIndex = i;
		}
	}
	return solutions[bestIndex].tickValue;
}

function getHeightOfRect(val, maxVal, propertiesObj ) {
  var maxHeight = $("#"+ propertiesObj["elemName"]).height() - propertiesObj.lowerMargin - properties.upperMargin;
  return val * maxHeight / maxVal;
}

function getWithOfRect(propertiesObj, data) {
  var withOfAllRects = $("#"+ propertiesObj["elemName"]).width() - propertiesObj.leftMargin - properties.rightMargin;
  var numberOfRects = Object.keys(data).length;
  return withOfAllRects / numberOfRects;
}

function getMaxYvalue (propertiesObj) {
  var height =  $("#"+ propertiesObj["elemName"]).height();
  return height - propertiesObj["lowerMargin"];
}

function getMinYvalue(propertiesObj) {
   return  propertiesObj["upperMargin"];
}

function getYpos(val, maxVal, propertiesObj) {
    var yMinPos = getMinYvalue(propertiesObj);
    var yMaxPos = getMaxYvalue(propertiesObj);
    var  deltaOfYpos = yMaxPos - yMinPos;
    var deltaYposForOneValUnit = deltaOfYpos /  maxVal;

    return yMaxPos - (val * deltaYposForOneValUnit);
}

function getXPos (iter, propertiesObj, withOfRect) {
  return propertiesObj.leftMargin + (iter * withOfRect) ;
}

function getPrecision (scale) {
	var logOfNum = Math.log10(scale);
	if (logOfNum >= 1) {
		return 0;
	} else {
		return - Math.floor(logOfNum) ;
	}
}

/* ---------------------------END OF HELPER FUNCTIONS-------------------------------------------*/

function drawChart(propertiesObj, resultJSON) {

	$(window).resize($("#"+propertiesObj.elemName).empty());

	var root = document.getElementById(propertiesObj.elemName);
	var svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
	svg.setAttribute('style', 'border: 1px solid black');
	svg.setAttribute('width', '100%');
	svg.setAttribute('height', '100%');
	svg.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xlink", "http://www.w3.org/1999/xlink");
	svg.setAttribute("class", "hoursInChart");
	root.appendChild(svg);


	var gForValues = document.createElementNS("http://www.w3.org/2000/svg", "g");

	var maxValInData = getMaxValue(resultJSON);
  var scale = getScale(maxValInData);
	var m = mFactorforVal(scale, maxValInData);
	var maxValOnScale = getMaxScaleValue (scale, m);

	for (var val = 0; val <= maxValOnScale ; val = val + scale) {

			var text = document.createElementNS("http://www.w3.org/2000/svg", "text");
			var precision = getPrecision(scale);
			var textValue = document.createTextNode( val.toFixed(precision) + " " + propertiesObj.yAxisUnit );
			text.appendChild(textValue);
			text.setAttribute("y", getYpos(val, maxValOnScale, propertiesObj ) );
			gForValues.appendChild(text);

	}
  svg.appendChild(gForValues);

  var i = 0;
	$.each(resultJSON, function(key, value) {

		var rect = document.createElementNS("http://www.w3.org/2000/svg", "rect");
		var g = document.createElementNS("http://www.w3.org/2000/svg", "g");
		var text = document.createElementNS("http://www.w3.org/2000/svg", "text");
		var textValue = document.createTextNode(key + propertiesObj.xAxisUnit);

		var height = getHeightOfRect(value, maxValOnScale, propertiesObj);
		var width = getWithOfRect(propertiesObj, resultJSON);
    var xPosition = getXPos(i, propertiesObj, width);
    var yPosition = getYpos(value, maxValOnScale, propertiesObj);

		rect.setAttribute("width", width );
		rect.setAttribute("height", height);
		rect.setAttribute("style","fill:rgb(50,50,255);");

		rect.setAttribute("y", yPosition);
		rect.setAttribute("x", xPosition );
		text.setAttribute("x", xPosition );
		text.setAttribute("y", getMaxYvalue(propertiesObj) + propertiesObj.distArgsChart );
		text.appendChild(textValue);
		g.appendChild(rect);
		g.appendChild(text);
		svg.appendChild(g);

		i = i + 1;
	});

}

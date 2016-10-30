function StringBuffer() { 
	this.array = new Array(); 
} 
StringBuffer.prototype.append = function(value) { 
	this.array[this.array.length] = value; 
	return this; 
} 
StringBuffer.prototype.toString = function() { 
	var _string = this.array.join(""); 
	return _string; 
}
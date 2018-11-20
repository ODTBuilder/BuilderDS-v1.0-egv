/**
 * 레이어 정보를 변경하는 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 06. 16
 * @version 0.01
 */
var gb;
if (!gb)
	gb = {};
if (!gb.edit)
	gb.edit = {};

gb.edit.TDSTYLE = {
	"padding": ".78571429em .78571429em"
}

gb.edit.TDKEYSTYLE = {
	"padding": ".78571429em .78571429em",
	"background": "rgba(0,0,0,.03)",
	"font-weight": "700"
}

gb.edit.TRSTYLE = {
	"border-bottom": "1px solid rgba(0,0,0,.1)"
}

gb.edit.INPUTSTYLE = {
	"width": "100%",
	"border": "1px solid rgba(34,36,38,.15)",
	"border-radius": ".28571429rem",
	"line-height": "1.21428571em",
	"padding": ".67857143em 1em"

}

gb.edit.LABELSTYLE = {
	"display": "inline-block",
	"background": "#e0e1e2 none",
	"color": "rgba(0,0,0,.6)",
	"margin": "0 .25em .25em 0",
	"padding": ".78571429em 1.5em .78571429em",
	"font-weight": "700",
	"line-height": "1em",
	"border-radius": ".28571429rem"
}

gb.edit.ModifyLayerProperties = function(obj) {
	var that = this;
	var options = obj;
	this.window;
	this.layer = undefined;
	this.serverInfo = undefined;
	this.layerName = undefined;
	this.srs = undefined;
	this.layerRecord = options.layerRecord ? options.layerRecord : undefined;
	this.featureRecord = options.featureRecord ? options.featureRecord : undefined;
	this.refer = options.refer ? options.refer : undefined;
	this.token = options.token || "";
	var xSpan = $("<span>").attr({
		"aria-hidden" : true
	}).html("&times;");
	var xButton = $("<button>").attr({
		"type" : "button",
		"data-dismiss" : "modal",
		"aria-label" : "Close"
	}).html(xSpan);
	$(xButton).addClass("close");

	this.htag = $("<h4>");
	this.htag.text("Layer Properties");
	$(this.htag).addClass("modal-title");

	var header = $("<div>").append(xButton).append(this.htag);
	$(header).addClass("modal-header");
	/*
	 * 
	 * 
	 * header end
	 * 
	 * 
	 */

	this.tbody = $("<tbody>");
	var tableTag = $("<table>").append(this.tbody);
	
	/*var group1 = this.createFormGroup({
		label: {
			"text": "Layer Name"
		},
		input: {
			"class": "form-control",
			"id": "layer-prop-name",
			"type": "text"
		}
	});
	
	var group4 = this.createFormGroup({
		label: {
			"text": "SRS"
		},
		input: {
			"class": "form-control",
			"id": "layer-prop-srs",
			"readonly": false,
			"type": "text"
		}
	});*/
	
	this.body = $("<div>").append(tableTag);
	
	// that.setForm("ngi", "layer");
	$(this.body).addClass("modal-body").css({"height": "550px", "overflow-y": "auto"});
	/*
	 * 
	 * 
	 * body end
	 * 
	 * 
	 */

	var closeBtn = $("<button>").attr({
		"type" : "button",
		"data-dismiss" : "modal"
	});
	$(closeBtn).addClass("btn");
	$(closeBtn).addClass("btn-default");
	$(closeBtn).text("Close");

	var okBtn = this.okBtn = $("<button>").attr({
		"type" : "button",
		"disabled": false
	}).on("click", function() {
		//var opt = that.getDefinitionForm();
		that.saveLayerProperties();
		that.close();
		that.refresh();
	});
	$(okBtn).addClass("btn");
	$(okBtn).addClass("btn-primary");
	$(okBtn).text("Save");

	var pright = $("<span>").css("float", "right");
	$(pright).append(closeBtn).append(okBtn);

	var footer = $("<div>").append(pright);
	$(footer).addClass("modal-footer");
	/*
	 * 
	 * 
	 * footer end
	 * 
	 * 
	 */
	var content = $("<div>").append(header).append(this.body).append(footer);
	$(content).addClass("modal-content");

	var dialog = $("<div>").html(content);
	$(dialog).addClass("modal-dialog");
	$(dialog).addClass("modal-lg");
	this.window = $("<div>").hide().attr({
		// Setting tabIndex makes the div focusable
		tabIndex : -1,
		role : "dialog",
	}).html(dialog);
	$(this.window).addClass("modal");
	$(this.window).addClass("fade");

	this.window.appendTo("body");
	this.window.modal({
		backdrop : true,
		keyboard : true,
		show : false,
	});
	
	$(".layer-prop-input").on("keyup", function(e){
		var bool = true;
		var id = e.target.id;
		var value = e.target.value;
		
		if(id === "propsrs"){
			if(value !== that.srs){
				bool = false;
			}
		}
		
		if(id === "proplName"){
			if(value !== that.layerName){
				bool = false;
			}
		}
		
		that.okBtn.attr({
			"disabled": bool
		});
	});
}
gb.edit.ModifyLayerProperties.prototype.createTableContent = function(obj){
	var list = obj || false;
	if(!list){
		return;
	}
	
	this.tbody.empty();
	
	var tr, key, value, label, labelKey, labelValue;
	for(var i in list){
		key = $("<td>").css(gb.edit.TDKEYSTYLE).text(i);
		if(list[i] instanceof Object){
			value = $("<td>").css(gb.edit.TDSTYLE);
			for(var j in list[i]){
				labelKey = $("<span>").text(j);
				if(list[i][j] instanceof Object){
					labelValue = $("<span>").text("[type:" + list[i][j].type + "]");
				} else {
					labelValue = $("<span>").text(list[i][j]);
				}
				label = $("<div>").css(gb.edit.LABELSTYLE).append(labelKey).append(labelValue);
				value.append(label);
			}
		} else {
			if(i === "lName" || i === "srs" || i === "title"){
				value = 
					$("<td>")
						.css(gb.edit.TDSTYLE)
						.append(
							$("<input>")
								.addClass("layer-prop-input")
								.attr({
									"id": "prop" + i,
									"value": list[i],
									"type": "text",
									"readonly": false
								})
								.css(gb.edit.INPUTSTYLE)
						);
			} else {
				value = $("<td>").css(gb.edit.TDSTYLE).text(list[i]);
			}
			
		}
		
		tr = $("<tr>").css(gb.edit.TRSTYLE).append(key).append(value);
		this.tbody.append(tr);
	}
	
	/*var label = options.label;
	
	var input = options.input;
	
	var labelTag = 
		$("<label>")
			.addClass("col-sm-2 col-form-label")
			.attr({
				"for": input.id
			})
			.text(label.text);
	
	var inputTag = 
		$("<input>")
			.addClass("layer-prop-input")
			.addClass(input["class"])
			.attr({
				"id": input.id,
				"type": input.type,
				"readonly": input.readonly || false
			});
	
	var inputDiv = $("<div>").addClass("col-sm-10").append(inputTag);
	var div = $("<div>").addClass("form-group row").append(labelTag).append(inputDiv);*/
}

gb.edit.ModifyLayerProperties.prototype.saveLayerInfo = function(){
	return 1;
}

gb.edit.ModifyLayerProperties.prototype.getLayerRecord = function() {
	return this.layerRecord;
};
gb.edit.ModifyLayerProperties.prototype.setLayerRecord = function(record) {
	this.layerRecord = record;
};
gb.edit.ModifyLayerProperties.prototype.getFeatureRecord = function() {
	return this.featureRecord;
};
gb.edit.ModifyLayerProperties.prototype.setFeatureRecord = function(record) {
	this.featureRecord = record;
};
gb.edit.ModifyLayerProperties.prototype.open = function() {
	this.window.modal('show');
};
gb.edit.ModifyLayerProperties.prototype.close = function() {
	this.window.modal('hide');
};
gb.edit.ModifyLayerProperties.prototype.setRefer = function(refer) {
	this.refer = refer;
};
gb.edit.ModifyLayerProperties.prototype.getRefer = function() {
	return this.refer;
};
gb.edit.ModifyLayerProperties.prototype.refresh = function() {
	this.refer.refresh();
};
gb.edit.ModifyLayerProperties.prototype.setLayer = function(layer) {
	this.layer = layer;
};
gb.edit.ModifyLayerProperties.prototype.getLayer = function() {
	return this.layer;
};
gb.edit.ModifyLayerProperties.prototype.setServerInfo = function(info) {
	this.serverInfo = info;
};
gb.edit.ModifyLayerProperties.prototype.getServerInfo = function() {
	return this.serverInfo;
};
gb.edit.ModifyLayerProperties.prototype.setInformation = function(info) {
	this.information = info;
};
gb.edit.ModifyLayerProperties.prototype.getInformation = function() {
	return this.information;
};


gb.edit.ModifyLayerProperties.prototype.setForm = function(info) {
	this.setInformation(info);
	this.getImageTileInfo("geoserver/getGeoLayerInfoList.ajax", info);
};

gb.edit.ModifyLayerProperties.prototype.getImageTileInfo = function(url, info) {
	var that = this;
	var geoserver = info.geoserver || false,
		workspace = info.workspace || false,
		datastore = info.datastore || false,
		layername = info.layername || false;
	
	if(!geoserver || !workspace || !datastore || !layername){
		console.error("Missed Parameter");
		return;
	}
	
	this.setServerInfo(info);
	
	var arr = {
		"serverName": geoserver,
		"workspace": workspace,
		"geoLayerList" : [ layername ]
	}
	$.ajax({
		url : url + this.token,
		method : "POST",
		contentType : "application/json; charset=UTF-8",
		cache : false,
		data : JSON.stringify(arr),
		beforeSend : function() { // 호출전실행
			$("body").css("cursor", "wait");
		},
		complete : function() {
			$("body").css("cursor", "default");
		},
		traditional : true,
		success : function(data, textStatus, jqXHR) {
			if (Array.isArray(data)) {
				if (data.length === 1) {
					var arra = [];
					
					that.setLayer(data[0]);
					that.layerName = data[0].lName;
					that.srs = data[0].srs;
					
					that.createTableContent(data[0]);
					
					/*$("#layer-prop-name").val(data[0].lName);
					$("#layer-prop-geom").val(data[0].geomType);
					$("#layer-prop-geomkey").val(data[0].geomkey);
					$("#layer-prop-srs").val(data[0].srs);
					$("#layer-prop-geoserver").val(geoserver);
					$("#layer-prop-workspace").val(workspace);
					$("#layer-prop-datastore").val(datastore);*/
				}

				$("body").css("cursor", "default");
			}
		}
	});
};

gb.edit.ModifyLayerProperties.prototype.saveLayerProperties = function() {
	var layer = this.layer;
	var serverInfo = this.getServerInfo();
	var arr = {
		"serverName": serverInfo.geoserver,
		"workspace": serverInfo.workspace,
		"datastore": serverInfo.datastore,
		"originalName": serverInfo.layername,
		"name": $("#proplName").val(),
		"title": $("#proptitle").val(),
		"srs": $("#propsrs").val()
	}
	
	$.ajax({
		url : "geoserver/updateLayer.ajax" + this.token,
		method : "POST",
		contentType : "application/json; charset=UTF-8",
		cache : false,
		data : JSON.stringify(arr),
		success: function(data, textStatus, jqXHR){
			console.log(data);
		}
	});
}
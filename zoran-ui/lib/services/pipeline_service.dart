import 'dart:convert';
import 'dart:html';

import 'package:json_annotation/json_annotation.dart';
import 'package:zoran.io/services/user_service.dart';

part 'pipeline_service.g.dart';

class PipelineService {
  final UserService _userService;
  final String _baseUrl;

  PipelineService(this._userService, this._baseUrl);

  Future<List<PipelineShort>> getAllPipelineData() async {
    try {
      final url = '$_baseUrl/api/ui/pipeline';
      final response = await HttpRequest.getString(url);
      final pipelines = json.decode(response);
      final result = pipelines
          .map((f) => PipelineShort.fromJson(f))
          .toList()
          .cast<PipelineShort>();
      return result;
    } catch (e) {
      return [];
    }
  }

  Future<PipelineShort> getPipelineDetails(String id) async {
    try {
      final url = '$_baseUrl/api/ui/pipeline/$id';
      final response = await HttpRequest.getString(url);
      final pipelines = json.decode(response);
      final result = pipelines
          .map((f) => PipelineShort.fromJson(f))
          .toList()
          .cast<PipelineShort>();
      return result;
    } catch (e) {
      rethrow;
    }
  }
}

@JsonSerializable(createToJson: true)
class Parameter {
  String name;
  String value;

  Parameter({
    this.name,
    this.value,
  });

  factory Parameter.fromJson(Map<String, dynamic> json) =>
      _$ParameterFromJson(json);

  Map<String, dynamic> toJson() => _$ParameterToJson(this);
}

@JsonSerializable(createToJson: true)
class Model {
  String name;
  String clazz;
  String description;
  List<Parameter> parameters;

  Model({
    this.name,
    this.description,
    this.parameters,
    this.clazz
  });

  factory Model.fromJson(Map<String, dynamic> json) =>
      _$ModelFromJson(json);

  Map<String, dynamic> toJson() => _$ModelToJson(this);
}

@JsonSerializable(createToJson: true)
class Pipeline {
  String name;
  List<Model> models;

  Pipeline(this.models);

  factory Pipeline.fromJson(Map<String, dynamic> json) =>
      _$PipelineFromJson(json);

  Map<String, dynamic> toJson() => _$PipelineToJson(this);
}

@JsonSerializable(createToJson: false)
class PipelineShort {
   String pipeLineId;
   String pipelineName;
   int noOfHandlers;

   PipelineShort(this.pipeLineId, this.pipelineName, this.noOfHandlers);

  factory PipelineShort.fromJson(Map<String, dynamic> json) =>
      _$PipelineShortFromJson(json);
}

@JsonSerializable(createToJson: false)
class PipelineDetails {
  String idDefinition;
  String idOwner;
  String idSharingGroup;
  String name;
  List<Task> tasks;

  PipelineDetails({
  this.idDefinition,
  this.idOwner,
  this.idSharingGroup,
  this.name,
  this.tasks,
  });

  factory PipelineDetails.fromJson(Map<String, dynamic> json) =>
      _$PipelineDetailsFromJson(json);
}

@JsonSerializable(createToJson: false)
class Task {
  Model handler;
  int order;
  Map<String, String> parameters;

  Task({
    this.handler,
    this.order,
    this.parameters,
  });

  factory Task.fromJson(Map<String, dynamic> json) =>
      _$TaskFromJson(json);
}



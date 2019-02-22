import 'dart:convert';
import 'dart:html';

import 'package:json_annotation/json_annotation.dart';
import 'package:zoran.io/services/user_service.dart';

part 'pipeline_service.g.dart';

class PipelineService {
  final String _baseUrl;

  List<Model> _models;
  List<Model> get models => _models == null ? getAllModels() : models;

  PipelineService(@zoranIoUrl this._baseUrl);

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

  Future<PipelineDetails> getPipelineDetails(String id) async {
    try {
      final url = '$_baseUrl/api/ui/pipeline/$id';
      final response = await HttpRequest.getString(url);
      final pipelines = json.decode(response);
      final result = PipelineDetails.fromJson(pipelines);
      return result;
    } catch (e) {
      return null;
    }
  }

  Future<int> savePipelineDetails(PipelineDetails details) async {
    try {
      final url = '$_baseUrl/api/ui/pipeline';
      final data = details.toJson();
      final response = await HttpRequest.postFormData(url, data);
      return response.status;
    } catch (e) {
      throw e;
    }
  }

  Future<List<Model>> getAllModels() async {
    try {
      final url = '$_baseUrl/api/ui/model/pipeline';
      final response = await HttpRequest.getString(url);
      final pipelines = json.decode(response);
      final result = pipelines['model']
          .map((f) => Model.fromJson(f))
          .toList()
          .cast<Model>();
      this._models = result;
      return result;
    } catch (e) {
      throw e;
    }
  }

  Future<Model> getModel(String clazz) async {
    try {
      final url = '$_baseUrl/api/ui/model/pipeline/$clazz';
      final response = await HttpRequest.getString(url);
      final pipelines = json.decode(response);
      final result = Model.fromJson(pipelines);
      return result;
    } catch (e) {
      return null;
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
   int noOfRuns;
   String lastCompleted;
   PipelineStatus status;

   PipelineShort(this.pipeLineId, this.pipelineName, this.noOfHandlers,
       this.noOfRuns, this.lastCompleted, this.status);

   factory PipelineShort.fromJson(Map<String, dynamic> json) =>
      _$PipelineShortFromJson(json);
}

enum PipelineStatus {
  COMPLETED,
  FAILED,
  STOPPED
}

@JsonSerializable(createToJson: true)
class PipelineDetails {
  String idDefinition;
  String idOwner;
  String idSharingGroup;
  String name;
  int noOfRuns;
  String lastRun;
  PipelineStatus status;
  List<Task> tasks = [];

  PipelineDetails(this.idDefinition, this.idOwner, this.idSharingGroup,
      this.name, this.noOfRuns, this.lastRun, this.status, this.tasks);

  PipelineDetails.init() {
    this.tasks = [];
  }

  factory PipelineDetails.fromJson(Map<String, dynamic> json) =>
      _$PipelineDetailsFromJson(json);

  Map<String, dynamic> toJson() => _$PipelineDetailsToJson(this);

}

@JsonSerializable(createToJson: true)
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

  Map<String, dynamic> toJson() => _$TaskToJson(this);

}
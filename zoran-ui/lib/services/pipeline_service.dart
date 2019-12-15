import 'dart:convert';
import 'dart:html';

import 'package:angular_router/angular_router.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:zoran.io/services/user_service.dart';

part 'pipeline_service.g.dart';

class PipelineService {
  final String _baseUrl;
  final Router _router;

  List<Model> _models;
  PipelineTask currentTask;

  PipelineService(@zoranIoUrl this._baseUrl, this._router);

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
      rethrow;
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
      rethrow;
    }
  }
//TODO does not seem to work
  Future<int> savePipelineDetails(PipelineDetails details) async {
    try {
      final url = '$_baseUrl/api/ui/pipeline';
      final data = json.encode(details.toJson());
      final response = await HttpRequest.request(url, method: 'POST',
        sendData: data, requestHeaders: {"Content-Type" : "application/json;charset=UTF-8"});
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

  Future<List<Model>> getModels() async {
    if(_models == null) {
      this._models = await getAllModels();
    }
    return _models;
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

  Future<PipelineTask> run(String id) async {
    try {
      final url = '$_baseUrl/api/ui/pipeline/$id/task';
      final response = await HttpRequest.postFormData(url, {});
      final pipelines = json.decode(response.response);
      final result = PipelineTask.fromJson(pipelines);
      this.currentTask = result;
      return result;
    } catch (e) {
      rethrow;
    }
  }

  Future<PipelineTask> check(String id) async {
    try {
      final url = '$_baseUrl/api/ui/pipeline/$id/task';
      final response = await HttpRequest.getString(url);
      if(response != null && response.isNotEmpty) {
        final pipelines = json.decode(response);
        final result = PipelineTask.fromJson(pipelines);
        this.currentTask = result;
        return result;
      }
      return null;
    } catch (e) {
      rethrow;
    }
  }

  Future<String> download(String id) async {
    try {
      final url = '$_baseUrl/api/ui/resource/$id/download';
//      final response = await HttpRequest.request(url, method: 'GET');
//      return new Blob([response.response]);
      return url;
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
   String id;
   String name;
   int handlers;
   int noOfRuns;
   String lastCompleted;
   PipelineStatus status;

   PipelineShort(this.id, this.name, this.handlers,
       this.noOfRuns, this.lastCompleted, this.status);

   factory PipelineShort.fromJson(Map<String, dynamic> json) =>
      _$PipelineShortFromJson(json);
}

enum PipelineStatus {
  COMPLETED,
  FAILED,
  STOPPED,
  IDLE
}

@JsonSerializable(createToJson: true)
class PipelineDetails {
  String idDefinition;
  String idOwner;
  String idSharingGroup;
  String name;
  int noOfRuns;
  String lastRun;
  String resourceId;
  PipelineStatus status;
  List<Task> tasks = [];
  List<String> listOfSharedUsers = [];

  PipelineDetails(this.idDefinition, this.idOwner, this.idSharingGroup,
      this.name, this.noOfRuns, this.lastRun, this.status, this.tasks,
      this.resourceId, this.listOfSharedUsers);

  PipelineDetails.init() {
    this.name = "New Undefined";
    this.tasks = [];
    this.listOfSharedUsers = [];
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
@JsonSerializable(createToJson: true)
class PipelineTask {
  String id;
  String idClient;
  String dateStart;
  String status;
  PipelineDetails definition;
  String resultPath;
  List<String> messages;

  PipelineTask(this.id, this.idClient, this.dateStart, this.status,
      this.definition, this.resultPath, this.messages);

  factory PipelineTask.fromJson(Map<String, dynamic> json) =>
      _$PipelineTaskFromJson(json);

  Map<String, dynamic> toJson() => _$PipelineTaskToJson(this);

}
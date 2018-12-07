import 'dart:async';
import 'dart:convert';
import 'dart:html';

import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:logging/logging.dart';
import 'package:sheets_dashboard/resource-page/resource_page_component.dart';

part 'zoran_service.g.dart';

/// Represents the base URL for HTTP requests using [ZoranService].
const zoranIoUrl = const OpaqueToken<String>('zoranBaseUrl');

class ZoranService extends Object {
  final Logger logger = new Logger('ZoranService');
  final String _baseUrl;

  ZoranService(@zoranIoUrl this._baseUrl);

  Future<VersionDto> getVersion() async {
    try {
      final url = '$_baseUrl/build-info';
      final response = await HttpRequest.getString(url);
      return new VersionDto.fromJson(json.decode(response));
    } catch (e, s) {
      logger.severe(e, s);
      rethrow;
    }
  }

  List<ResourceModuleDto> getResources() {
    try {
      final url = '$_baseUrl/app/version';
//      final response = await HttpRequest.getString(url);
//      return new UserDto.fromJson(json.decode(response));
      return [new ResourceModuleDto("Your Project One", "Public",
          "You!", "Java Project", "fakeId"),
      new ResourceModuleDto("Your Project Two", "Public",
          "You!", "Java Project", "fakeId")
      ];
    } catch (e, s) {
      logger.severe(e, s);
      rethrow;
    }
  }

  NewResourceModel getNewResourceModel() {
    try {
//      final url = '$_baseUrl/app/version';
//      final response = await HttpRequest.getString(url);
//      return new UserDto.fromJson(json.decode(response));
      return new NewResourceModel("0.0.1-SNAPSHOT", ["Java"], [
        new LanguageDependenciesModel("Java", ["H2", "JDBC"]),
        new LanguageDependenciesModel("Groovy", ["PowerMock"]),
      ]);
    } catch (e, s) {
      logger.severe(e, s);
      rethrow;
    }
  }

  ProjectDetails getResourceByItsId(String uniqueId) {
    try {
//      final url = '$_baseUrl/app/version';
//      final response = await HttpRequest.getString(url);
//      return new UserDto.fromJson(json.decode(response));
      return new ProjectDetails("someuri", "test1", "Test1", "Java", "zoran.io", "core",
          "0.0.1", "Test Project lead", "describtion is very long and boring describtion is"
              " very long and boring describtion is very long and boring describtion is very "
              "long and boring describtion is very long and boring ", "Java", "PUBLIC", null, "Zoran",
          null, null, null);
    } catch (e, s) {
      logger.severe(e, s);
      rethrow;
    }
  }

  List<ProjectDetails> getAvailableResource() {
    try {
//      final url = '$_baseUrl/app/version';
//      final response = await HttpRequest.getString(url);
//      return new UserDto.fromJson(json.decode(response));
        return [
          new ProjectDetails(null, "test1", "Test1", "Java", null, null,
              null, "Test Project lead", null, "Java", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test2", "Test2", "Kotlin", null, null,
              null, "Test2 Project lead", null, "Kotlin", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test1", "Test1", "Java", null, null,
              null, "Test Project lead", null, "Java", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test2", "Test2", "Kotlin", null, null,
              null, "Test2 Project lead", null, "Kotlin", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test1", "Test1", "Java", null, null,
              null, "Test Project lead", null, "Java", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test2", "Test2", "Kotlin", null, null,
              null, "Test2 Project lead", null, "Kotlin", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test1", "Test1", "Java", null, null,
              null, "Test Project lead", null, "Java", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test2", "Test2", "Kotlin", null, null,
              null, "Test2 Project lead", null, "Kotlin", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test1", "Test1", "Java", null, null,
              null, "Test Project lead", null, "Java", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test2", "Test2", "Kotlin", null, null,
              null, "Test2 Project lead", null, "Kotlin", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test1", "Test1", "Java", null, null,
              null, "Test Project lead", null, "Java", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test2", "Test2", "Kotlin", null, null,
              null, "Test2 Project lead", null, "Kotlin", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test1", "Test1", "Java", null, null,
              null, "Test Project lead", null, "Java", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test2", "Test2", "Kotlin", null, null,
              null, "Test2 Project lead", null, "Kotlin", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test1", "Test1", "Java", null, null,
              null, "Test Project lead", null, "Java", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test2", "Test2", "Kotlin", null, null,
              null, "Test2 Project lead", null, "Kotlin", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test1", "Test1", "Java", null, null,
              null, "Test Project lead", null, "Java", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test2", "Test2", "Kotlin", null, null,
              null, "Test2 Project lead", null, "Kotlin", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test1", "Test1", "Java", null, null,
              null, "Test Project lead", null, "Java", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test2", "Test2", "Kotlin", null, null,
              null, "Test2 Project lead", null, "Kotlin", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test1", "Test1", "Java", null, null,
              null, "Test Project lead", null, "Java", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test2", "Test2", "Kotlin", null, null,
              null, "Test2 Project lead", null, "Kotlin", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test1", "Test1", "Java", null, null,
              null, "Test Project lead", null, "Java", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test2", "Test2", "Kotlin", null, null,
              null, "Test2 Project lead", null, "Kotlin", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test1", "Test1", "Java", null, null,
              null, "Test Project lead", null, "Java", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test2", "Test2", "Kotlin", null, null,
              null, "Test2 Project lead", null, "Kotlin", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test1", "Test1", "Java", null, null,
              null, "Test Project lead", null, "Java", null, null, "Zoran",
              null, null, null),
          new ProjectDetails(null, "test2", "Test2", "Kotlin", null, null,
              null, "Test2 Project lead", null, "Kotlin", null, null, "Zoran",
              null, null, null)
        ];
    } catch (e, s) {
      logger.severe(e, s);
      rethrow;
    }
  }
}

@JsonSerializable(createToJson: false)
class VersionDto {
  final String branch;
  final String commitIdAbbrev;
  final String buildHost;
  final String buildTime;
  final String buildVersion;

  VersionDto(this.branch, this.commitIdAbbrev,
      this.buildHost, this.buildTime, this.buildVersion);

  factory VersionDto.fromJson(Map<String, dynamic> json) =>
      _$VersionDtoFromJson(json);
}

@JsonSerializable(createToJson: true)
class ProjectDetails implements HasUIDisplayName {
  @JsonKey(required: false)
  String uri;
  String projectName;
  String name;
  String projectLanguage;
  String groupId;
  String artifactId;
  @JsonKey(required: false)
  String version;
  @JsonKey(required: false)
  String lead; //short lead into template
  @JsonKey(required: false)
  String description; //markdown
  @JsonKey(required: false)
  String tags;
  @JsonKey(required: false)
  String visibility;
  @JsonKey(required: false)
  String yaml;
  @JsonKey(required: false)
  String owner;
  @JsonKey(required: false)
  String buildApp;
  @JsonKey(required: false)
  List dependencies = [];
  @JsonKey(required: false)
  List templates = [];
  @JsonKey(ignore: true)
  List<ProjectDetails> details = [];

  ProjectDetails.empty(this.owner);

  ProjectDetails(this.uri, this.projectName, this.name, this.projectLanguage,
      this.groupId, this.artifactId, this.version, this.lead, this.description,
      this.tags, this.visibility, this.yaml, this.owner, this.buildApp,
      this.dependencies, this.templates);

  factory ProjectDetails.fromJson(Map<String, dynamic> json) =>
      _$ProjectDetailsFromJson(json);

  Map<String, dynamic> toJson() => _$ProjectDetailsToJson(this);

  void set pName(String bom) => this.projectName = bom;
  void set rName(String bom) => this.name = bom;
  void set language(String bom) => this.projectLanguage = bom;
  void set group(String bom) => this.groupId = bom;
  void set artifact(String bom) => this.artifactId = bom;
  void set v(String bom) => this.version = bom;

  // TODO: implement uiDisplayName
  @override
  String get uiDisplayName => owner + "/" + projectName;
}

@JsonSerializable(createToJson: false)
class NewResourceModel {
  String version;
  List<String> languages;
  List<LanguageDependenciesModel> dependencies; //language - list of dependencies

  factory NewResourceModel.fromJson(Map<String, dynamic> json) =>
      _$NewResourceModelFromJson(json);

  NewResourceModel(this.version, this.languages, this.dependencies);
}

@JsonSerializable(createToJson: false)
class LanguageDependenciesModel {
  String language;
  List<String> dependencies;

  factory LanguageDependenciesModel.fromJson(Map<String, dynamic> json) =>
      _$LanguageDependenciesModelFromJson(json);

  LanguageDependenciesModel(this.language, this.dependencies);
}
import 'dart:async';
import 'dart:convert';
import 'dart:html';

import 'package:angular/angular.dart';
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
class ProjectDetails {
  String billOfMaterials;
  String projectName;
  String name;
  String projectLanguage;
  String groupId;
  String artifactId;
  String version;
  String description;
  String tags;
  String visibility;
  @JsonKey(required: false)
  String yaml;
  String buildApp;
  List dependencies = [];

  ProjectDetails.empty();


  ProjectDetails(this.billOfMaterials, this.projectName, this.name,
      this.projectLanguage, this.groupId, this.artifactId, this.version,
      this.description, this.tags, this.yaml, this.buildApp, this.dependencies);

  factory ProjectDetails.fromJson(Map<String, dynamic> json) =>
      _$ProjectDetailsFromJson(json);

  Map<String, dynamic> toJson() => _$ProjectDetailsToJson(this);

  void set bom(String bom) => this.billOfMaterials = bom;
  void set pName(String bom) => this.projectName = bom;
  void set rName(String bom) => this.name = bom;
  void set language(String bom) => this.projectLanguage = bom;
  void set group(String bom) => this.groupId = bom;
  void set artifact(String bom) => this.artifactId = bom;
  void set v(String bom) => this.version = bom;
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
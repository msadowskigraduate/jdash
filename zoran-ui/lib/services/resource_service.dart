import 'dart:convert';
import 'dart:html';

import 'package:angular_components/angular_components.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:logging/logging.dart';
import 'package:zoran.io/services/user_service.dart';
import 'package:zoran.io/services/yml_parser_utils.dart';
import 'package:zoran.io/services/zoran_service.dart';

part 'resource_service.g.dart';

class NewResourceService {
  final Logger _logger = new Logger('ZoranService');
  final UserService _userService;
  final String _baseUrl;

  NewResourceRequest request; //Request data is persisted via service

  List<LanguageDependenciesModel> dependencies;
  String buildApp;

  NewResourceService(this._userService, @zoranIoUrl this._baseUrl);

  void createNewRequest() async { //Initialize new empty Request
    this.request = NewResourceRequest.empty();
    await _userService.getCurrentUser();
    this.request.owner = _userService.user.name; //null!
  }

  void createNewRequestFromYML(String yml) {
    this.request = YMLParserUtils.fromYML(yml);
  }

  void clearRequest() {
    this.request = null;
  }

  Future<ResourceResponse> postNewResourceRequest() async {
    try {
      final url = '$_baseUrl/api/ui/resource';
      request.templatesUsed = request.templatesUsed.map((f) => f.id).cast<String>().toList();
      request.type = setupType();
      final response = await HttpRequest.request(url, method: 'POST',
        sendData: jsonEncode(this.request.toJson()), requestHeaders:
          {"Content-Type" : "application/json;charset=UTF-8"});
      final detaillist = json.decode(response.responseText);
      return ResourceResponse.fromJson(detaillist);
    } catch (e, s) {
      _logger.severe(e, s);
      rethrow;
    }
  }

  ResourceType setupType() {
    switch(buildApp) {
      case "Maven": return ResourceType.MAVEN_PROJECT;
      case "Gradle": return ResourceType.GRADLE_PROJECT;
      default: return ResourceType.MAVEN_PROJECT;
    }
  }

  Future<List<TemplateDataTuple>> getTemplateMetadata(String params) async {
    try {
      final url = '$_baseUrl/api/ui/model/template?slugs=$params';
      final response = await HttpRequest.getString(url);
      final modelList = json.decode(response) as List;
      final result = modelList
          .map((f) => TemplateDataTuple.fromJson(f))
          .toList()
          .cast<TemplateDataTuple>();
      return result;
    } catch (e, s) {
      rethrow;
    }
  }
}
//==============================================================================
/**
 * Entity used to collect information about new resource in Resource Wizard.
 */
@JsonSerializable(createToJson: true)
class NewResourceRequest {
  String name;
  String projectLanguage;
  String groupId;
  String artifactId;
  ResourceType type;
  ResourceVisibility resourceVisibility;
  String version;
  String lead;
  String description;
  String gitUrl;
  String tags;
  //SPRING BOOT DEPENDENCIES
  List templatesUsed;
  List<String> templateData;
  List<TemplateDataTuple> templateTuples;
  String licenseKey;
  String bootVersion;
  String javaVersion;

  //Owner is verified on the backend. Here it is used only in presentation
  @JsonKey(ignore: true)
  String owner;

  NewResourceRequest(this.name, this.projectLanguage, this.groupId,
      this.artifactId, this.type, this.resourceVisibility, this.version,
      this.lead, this.description, this.tags, this.templatesUsed,
      this.licenseKey, this.gitUrl, this.bootVersion, this.javaVersion,
      this.templateData);

  factory NewResourceRequest.empty() => new NewResourceRequest("", "", "", "",
      ResourceType.PROJECT, ResourceVisibility.PUBLIC, "", "", "", "", [], "",
      "","","", []);

  factory NewResourceRequest.fromJson(Map<String, dynamic> json) =>
      _$NewResourceRequestFromJson(json);

  Map<String, dynamic> toJson() => _$NewResourceRequestToJson(this);
}

@JsonSerializable(createToJson: true)
class TemplateDataTuple {
  String templateSlug;
  List<Context> contexts;

  TemplateDataTuple(this.templateSlug, this.contexts);

  factory TemplateDataTuple.fromJson(Map<String, dynamic> json) =>
      _$TemplateDataTupleFromJson(json);

  Map<String, dynamic> toJson() => _$TemplateDataTupleToJson(this);
}
@JsonSerializable(createToJson: true)
class Context {
  String name;
  String value;

  Context(this.name, this.value);

  factory Context.fromJson(Map<String, dynamic> json) =>
      _$ContextFromJson(json);

  Map<String, dynamic> toJson() => _$ContextToJson(this);
}
//=============================================================================
/**
 * Entity used to hold information on existing resources retrieved from app.
 */
@JsonSerializable()
class ResourceResponse implements HasUIDisplayName {
  String id;
  String name;
  String projectLanguage;
  String groupId;
  String artifactId;
  ResourceType type;
  ResourceVisibility resourceVisibility;
  String owner;
  String version;
  String lead;
  String description;
  String tags;
  String gitUrl;
  License license;
  List templatesUsed;
  List templateData;

  ResourceResponse(this.id, this.name, this.projectLanguage, this.groupId,
      this.artifactId, this.type, this.resourceVisibility, this.owner,
      this.version, this.lead, this.description, this.tags, this.gitUrl,
      this.license, this.templatesUsed, this.templateData);

  factory ResourceResponse.fromJson(Map<String, dynamic> json) =>
      _$ResourceResponseFromJson(json);

  @override
  String get uiDisplayName => owner + "/" + name;
}
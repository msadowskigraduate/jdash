import 'dart:async';
import 'dart:convert';
import 'dart:html';

import 'package:angular/angular.dart';
import 'package:http/http.dart' as http;
import 'package:json_annotation/json_annotation.dart';
import 'package:logging/logging.dart';
import 'package:sheets_dashboard/resource-page/resource_page_component.dart';

part 'zoran_service.g.dart';

/// Represents the base URL for HTTP requests using [ZoranService].
const zoranIoUrl = const OpaqueToken<String>('zoranBaseUrl');

class ZoranService extends Object {
  final Logger logger = new Logger('ZoranService');
  final String _baseUrl = zoranIoUrl.toString();

  Future<VersionDto> getVersion() async {
    try {
      final url = '$_baseUrl/app/version';
      final response = await HttpRequest.getString(url);
      return new VersionDto.fromJson(json.decode(response));
    } catch (e, s) {
      logger.severe(e, s);
      rethrow;
    }
  }

  UserDto isAuthenticated()  {
    try {
      final url = '$_baseUrl/app/version';
//      final response = await HttpRequest.getString(url);
//      return new UserDto.fromJson(json.decode(response));
      return new UserDto("login", "FakeName", "ACTIVE", "fak"
          "e@fake.com",
          "https://www.google.ch/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiOyqb34fHeAhVNzKQKHUN5AiAQjRx6BAgBEAU&url=https%3A%2F%2Fwww.colourbox.com%2Fvector%2Fgiraffe-avatar-wearing-glasses-vector-11051832&psig=AOvVaw3YvvS-QIc_jW4114jNfVfl&ust=1543311574965113",
          "github.com", "fake.com", "USER", "10-10-2010");
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
          "You!", "Java Project", "fakeId")];
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

@JsonSerializable(createToJson: false)
class UserDto {
  final String login;
  final String name;
  final String state;
  final String email;
  final String avatarUrl;
  final String repoUrl;
  final String htmlUrl;
  final String userType;
  final String lastLogin;

  UserDto(this.login, this.name, this.state, this.email, this.avatarUrl,
      this.repoUrl, this.htmlUrl, this.userType, this.lastLogin);

  factory UserDto.fromJson(Map<String, dynamic> json) =>
      _$UserDtoFromJson(json);
}
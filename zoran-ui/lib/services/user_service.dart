import 'dart:convert';
import 'dart:html';

import 'package:angular/core.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:logging/logging.dart';

part 'user_service.g.dart';

const zoranIoUrl = const OpaqueToken<String>('zoranBaseUrl');

class UserService {
  final Logger logger = new Logger('ZoranService');
  final String _baseUrl;
  UserDto _user;
  UserService(@zoranIoUrl this._baseUrl);

  UserDto get user => _user;

  bool isAuthenticated()  {
    return true;
  }

  Future<UserDto> getCurrentUser() async {
    try {
      final url = '$_baseUrl/userinfo';
      final response = await HttpRequest.getString(url);
      UserDto user = new UserDto.fromJson(json.decode(response));
      this._user = user;
      return this.user;
    } catch (e, s) {
      logger.severe(e, s);
      rethrow;
    }
  }

  Future<UserDto> activateUser() async {
    try {
      final url = '$_baseUrl/iaccept';
      final response = await HttpRequest.getString(url);
      UserDto user = new UserDto.fromJson(json.decode(response));
      this._user = user;
      return this.user;
    } catch (e, s) {
      logger.severe(e, s);
      rethrow;
    }
  }

  Future<UserDto> deactivateUser() async {
    try {
      final url = '$_baseUrl/banme';
      final response = await HttpRequest.getString(url);
      UserDto user = new UserDto.fromJson(json.decode(response));
      this._user = user;
      return this.user;
    } catch (e, s) {
      logger.severe(e, s);
      rethrow;
    }
  }
}

@JsonSerializable(createToJson: false)
class UserDto {
  String login;
  String name;
  String state;
  String email;
  String avatarUrl;
  String repoUrl;
  String htmlUrl;
  String userType;
  String lastLogin;

  UserDto(this.login, this.name, this.state, this.email, this.avatarUrl,
      this.repoUrl, this.htmlUrl, this.userType, this.lastLogin);

  factory UserDto.fromJson(Map<String, dynamic> json) =>
      _$UserDtoFromJson(json);
}
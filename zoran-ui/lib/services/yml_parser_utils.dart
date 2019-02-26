import 'package:yaml/yaml.dart';
import 'dart:convert';

import 'package:zoran.io/services/resource_service.dart';

// Parses YML to Resource-describing object
class YMLParserUtils {
  static final String YML_NAME_STRING = 'name';
  static final String YML_LANG_STRING = 'lang';
  static final String YML_GROUP_ID_STRING = 'groupId';
  static final String YML_ARTIFACT_ID_STRING = 'artifactId';
  static final String YML_TYPE_STRING = 'type';
  static final String YML_VISIBILITY_STRING = 'visibility';
  static final String YML_VERSION_STRING = 'version';
  static final String YML_LEAD_STRING = 'lead';
  static final String YML_DESC_STRING = 'description';
  static final String YML_TAGS_STRING = 'tags';
  static final String YML_DEPENDENCIES_STRING = 'dependencies';
  static final String YML_TEMPLATES_STRING = 'templates';
  static final String YML_LICENSE_STRING = 'license';
  static final String YML_GIT_URL_STRING = 'gitUrl';
  static final String YML_BOOT_VERSION_STRING = 'bootVersion';
  static final String YML_JAVA_VERSION_STRING = 'javaVersion';

  static String parseYML(String yml) {
    var doc = loadYaml(yml);
    return jsonEncode(doc);
  }

  static NewResourceRequest fromYML(String yml) {
    var doc = loadYaml(yml);
    return NewResourceRequest(
        doc[YML_NAME_STRING],
        doc[YML_LANG_STRING],
        doc[YML_GROUP_ID_STRING],
        doc[YML_ARTIFACT_ID_STRING],
        doc[YML_TYPE_STRING],
        doc[YML_VISIBILITY_STRING],
        doc[YML_VERSION_STRING],
        doc[YML_LEAD_STRING],
        doc[YML_DESC_STRING],
        doc[YML_TAGS_STRING],
        doc[YML_DEPENDENCIES_STRING],
        doc[YML_LICENSE_STRING],
        doc[YML_GIT_URL_STRING],
        doc[YML_BOOT_VERSION_STRING],
        doc[YML_JAVA_VERSION_STRING],
        doc[YML_TEMPLATES_STRING]
    );
  }
}

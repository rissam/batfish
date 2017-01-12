parser grammar Cisco_snmp;

import Cisco_common;

options {
   tokenVocab = CiscoLexer;
}

s_snmp_server
:
   SNMP_SERVER
   (
      NEWLINE
      | ss_community
      | ss_enable_mib_null
      | ss_enable_traps
      | ss_host
      | ss_null
      | ss_trap_source
   )
;

ss_community
:
   COMMUNITY name = variable
   (
      ssc_group
      | ssc_use_ipv4_acl
      | ssc_use_ipv6_acl
      | ssc_access_control
   )
;

ss_enable_mib_null
:
   ENABLE MIB variable NEWLINE
;

ss_enable_traps
:
   ENABLE TRAPS
   (
      snmp_trap_type = variable
      (
         subfeature += variable
      )*
   )? NEWLINE
;

ss_host
:
   HOST
   (
      ip4 = IP_ADDRESS
      | ip6 = IPV6_ADDRESS
      | host = variable
   )
   (
      ss_host_null
      | ss_host_use_vrf
   )
;

ss_host_null
:
   (
      INFORMS
      | TRAPS
      | VERSION
   ) ~NEWLINE* NEWLINE
;

ss_host_use_vrf
:
   USE_VRF vrf = variable NEWLINE
;

ss_host_version
:
   VERSION
;

ss_null
:
   (
      AAA
      | AAA_USER
      | CHASSIS_ID
      | COMMUNITY_MAP
      | CONTACT
      | CONTEXT
      | ENGINEID
      | GLOBALENFORCEPRIV
      | GROUP
      | IFINDEX
      | LOCATION
      | LOGGING
      | MANAGER
      | MAX_IFINDEX_PER_MODULE
      | OVERLOAD_CONTROL
      | PRIORITY
      | PROTOCOL
      | QUEUE_LENGTH
      | SOURCE_INTERFACE
      | TCP_SESSION
      | TRAP
      | TRAPS
      | USER
      | VIEW
      | VRF
   ) ~NEWLINE* NEWLINE
;

ss_trap_source
:
   TRAP_SOURCE IPV4? interface_name NEWLINE
;

ssc_access_control
:
   (
      RO
      | RW
      | SDROWNER
      | SYSTEMOWNER
      |
      (
         VIEW view = variable_snmp
      )
   )*
   (
      (
         (
            IPV4 acl4 = variable_snmp
         )
         |
         (
            IPV6 acl6 = variable_snmp
         ) DEC?
      )
      | acl4 = variable_snmp
   )* NEWLINE
;

ssc_group
:
   GROUP name = variable NEWLINE
;

ssc_use_ipv4_acl
:
   (
      USE_ACL
      | USE_IPV4_ACL
   ) name = variable NEWLINE
;

ssc_use_ipv6_acl
:
   USE_IPV6_ACL name = variable NEWLINE
;

variable_snmp
:
   ~( IPV4 | IPV6 | GROUP | NEWLINE | RO | RW | SDROWNER | SYSTEMOWNER |
   USE_ACL | USE_IPV4_ACL | USE_IPV6_ACL | VIEW )
;
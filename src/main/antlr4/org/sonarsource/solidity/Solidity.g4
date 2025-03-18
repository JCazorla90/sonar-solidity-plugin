grammar Solidity;

sourceUnit: (pragmaDirective | importDirective | contractDefinition)* EOF;

pragmaDirective: 'pragma' 'solidity' versionRange ';';
versionRange: versionLiteral | versionLiteral '+' | versionLiteral '^';

contractDefinition: 'contract' Identifier '{' contractBody '}';
contractBody: (functionDefinition | stateVariableDeclaration)*;

functionDefinition: 'function' Identifier '(' parameterList? ')' (modifierList)? (returns '(' returnParameters ')')? block;
parameterList: parameter (',' parameter)*;
parameter: typeName Identifier?;
typeName: elementaryTypeName | userDefinedTypeName;
elementaryTypeName: 'address' | 'bool' | 'uint256' | 'bytes32' | 'string';
block: '{' statement* '}';
statement: ifStatement | expressionStatement | uncheckedBlock;
uncheckedBlock: 'unchecked' block;

expressionStatement: expression ';';
expression: primaryExpression | expression ('+' | '-') expression;
primaryExpression: Identifier | literal;

modifierList: ('public' | 'private' | 'internal' | 'external')*; // Ejemplo básico
returnParameters: parameterList;
stateVariableDeclaration: typeName Identifier ';'; // Ejemplo básico
importDirective: 'import' StringLiteral ';'; // Ejemplo básico
ifStatement: 'if' '(' expression ')' statement; // Ejemplo básico

// Tokens (lexer rules)
Identifier: [a-zA-Z_][a-zA-Z0-9_]*;
NumberLiteral: [0-9]+;
StringLiteral: '"' .*? '"';
versionLiteral: [0-9]+ '.' [0-9]+ '.' [0-9]+;

WS: [ \t\r\n]+ -> skip; // Ignorar espacios en blanco

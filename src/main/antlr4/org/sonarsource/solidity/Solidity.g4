grammar Solidity;

sourceUnit: (pragmaDirective | importDirective | contractDefinition)* EOF;

pragmaDirective: 'pragma' 'solidity' versionRange ';';
versionRange: versionLiteral | versionLiteral '+' | versionLiteral '^';

contractDefinition: 'contract' identifier '{' contractBody '}';
contractBody: (functionDefinition | stateVariableDeclaration)*;

functionDefinition: 'function' identifier '(' parameterList? ')' (modifierList)? (returns '(' returnParameters ')')? block;
parameterList: parameter (',' parameter)*;
parameter: typeName identifier?;
typeName: elementaryTypeName | userDefinedTypeName;
elementaryTypeName: 'address' | 'bool' | 'uint256' | 'bytes32' | 'string';
block: '{' statement* '}';
statement: ifStatement | expressionStatement | uncheckedBlock;
uncheckedBlock: 'unchecked' block;

expressionStatement: expression ';';
expression: primaryExpression | expression ('+' | '-') expression;
primaryExpression: identifier | literal;

identifier: [a-zA-Z_][a-zA-Z0-9_]*;
literal: NumberLiteral | StringLiteral;
NumberLiteral: [0-9]+;
StringLiteral: '"' .*? '"';

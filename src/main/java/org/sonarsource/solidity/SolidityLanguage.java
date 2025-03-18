package org.sonarsource.solidity;

import org.sonar.api.resources.AbstractLanguage;

public class SolidityLanguage extends AbstractLanguage {

    public static final String KEY = "solidity";
    public static final Predicate<InputFile> PREDICATE = inputFile -> inputFile.filename().endsWith(".sol");

    public SolidityLanguage() {
        super(KEY, "Solidity");
    }

    @Override
    public String[] getFileSuffixes() {
        return new String[] {".sol"};
    }
}

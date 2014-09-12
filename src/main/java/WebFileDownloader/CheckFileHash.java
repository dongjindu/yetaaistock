/*
 * Copyright (c) 2014, Yetaai
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package WebFileDownloader;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 *
 * @author Yetaai
 */
public class CheckFileHash {
 
    private static final Logger LOG = Logger.getLogger(CheckFileHash.class);
    private HashType typeOfHash = null;
    private String expectedFileHash = null;
    private File fileToCheck = null;
 
    /**
     * The File to perform a Hash check upon
     *
     * @param fileToCheck
     * @throws FileNotFoundException
     */
    public void fileToCheck(File fileToCheck) throws FileNotFoundException {
        if (!fileToCheck.exists()) throw new FileNotFoundException(fileToCheck + " does not exist!");
 
        this.fileToCheck = fileToCheck;
    }
 
    /**
     * Hash details used to perform the Hash check
     *
     * @param hash
     * @param hashType
     */
    public void hashDetails(String hash, HashType hashType) {
        this.expectedFileHash = hash;
        this.typeOfHash = hashType;
    }
 
    /**
     * Performs a expectedFileHash check on a File.
     *
     * @return
     * @throws IOException
     */
    public boolean hasAValidHash() throws IOException {
        if (this.fileToCheck == null) throw new FileNotFoundException("File to check has not been set!");
        if (this.expectedFileHash == null || this.typeOfHash == null) throw new NullPointerException("Hash details have not been set!");
 
        String actualFileHash = "";
        boolean isHashValid = false;
 
        switch (this.typeOfHash) {
            case MD5:
                actualFileHash = DigestUtils.md5Hex(new FileInputStream(this.fileToCheck));
                if (this.expectedFileHash.equals(actualFileHash)) isHashValid = true;
                break;
            case SHA1:
                actualFileHash = DigestUtils.shaHex(new FileInputStream(this.fileToCheck));
                if (this.expectedFileHash.equals(actualFileHash)) isHashValid = true;
                break;
        }
 
        LOG.info("Filename = '" + this.fileToCheck.getName() + "'");
        LOG.info("Expected Hash = '" + this.expectedFileHash + "'");
        LOG.info("Actual Hash = '" + actualFileHash + "'");
 
        return isHashValid;
    }
     
}

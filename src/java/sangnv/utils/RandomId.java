/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.utils;

import java.util.UUID;

/**
 *
 * @author Shang
 */
public class RandomId {

    public static String generateId() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
    }

}

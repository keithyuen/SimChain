import java.util.ArrayList;

import com.google.gson.GsonBuilder;

public class SimChain {

  public static int difficulty = 2;
  public static int counter;
  public static ArrayList<Block> blockchain = new ArrayList<Block>();

  public static void main(String[] args) {

    // Block genesisBlock = new Block("Hi im the first block", "0");
    // Block secondBlock = new Block("Hello im the second block",
    // genesisBlock.hash);
    // Block thirdBlock = new Block("Heyyy im the third block",
    // secondBlock.hash);
    // System.out.println("Hash for block 1 : " + genesisBlock.hash);
    // System.out.println("Hash for block 2 : " + secondBlock.hash);
    // System.out.println("Hash for block 3 : " + thirdBlock.hash);

    // add our blocks to the blockchain ArrayList:
    blockchain.add(new Block("Hi! This is the initial block...", "0"));
    System.out.println("Mining Core block... ");
    blockchain.get(0).mineBlock(difficulty);
    for(int i = 1; i<100; i++){
      blockchain.add(new Block("Block number "+i+"", blockchain.get(blockchain.size() - 1).hash));
      System.out.println("Mine block "+i+"... ");
      blockchain.get(i).mineBlock(difficulty);
      counter = i;
    }

    System.out.println("\nBlockchain is Valid: " + isChainValid());

    String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
    System.out.println("\nThe block chain: ");
    System.out.println(blockchainJson);
    System.out.println("\nTotal blocks mined : "+(counter + 1));
  }

  public static Boolean isChainValid() {
    Block currentBlock;
    Block previousBlock;
    String hashTarget = new String(new char[difficulty]).replace('\0', '0');

    // loop through blockchain to check hashes:
    for (int i = 1; i < blockchain.size(); i++) {
      currentBlock = blockchain.get(i);
      previousBlock = blockchain.get(i - 1);

      // compare registered hash and calculated hash:
      if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
        System.out.println("Current Hashes not equal");
        return false;
      }

      // compare previous hash and registered previous hash
      if (!previousBlock.hash.equals(currentBlock.previousHash)) {
        System.out.println("Previous Hashes not equal");
        return false;
      }

      // check if hash is solved
      if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
        System.out.println("This block hasn't been mined");
        return false;
      }
    }
    return true;
  }
}

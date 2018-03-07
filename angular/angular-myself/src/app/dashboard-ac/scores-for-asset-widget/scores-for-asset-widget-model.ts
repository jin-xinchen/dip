/**
 * @description showing page data model
 */
export interface RecentScoresForAsset {
  name: string;
  ASSET_CONTROL_SYSID: string | number;
  ASSET_SYSID: string|number;
  USER_MODIFY_DATETIME: any;
  formattedDate: any;
  current_D_TOTAL_RATING_SCORE_NUM: number;
  recent_D_TOTAL_RATING_SCORE_NUM: number;
  difference?: number;
  dashtype?: string;
  spinner?: boolean;
  divID?: string;

  colour?: string;
  icon?: string;
  //error?:string; 
}